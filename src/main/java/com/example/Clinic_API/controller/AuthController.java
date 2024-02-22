package com.example.Clinic_API.controller;

import com.example.Clinic_API.convert.UserConvert;
import com.example.Clinic_API.entities.User;
import com.example.Clinic_API.enums.ResponseCode;
import com.example.Clinic_API.payload.*;
import com.example.Clinic_API.repository.RoleRepository;
import com.example.Clinic_API.repository.UserRepository;
import com.example.Clinic_API.security.CustomUserDetails;
import com.example.Clinic_API.security.JwtTokenProvider;
import com.example.Clinic_API.service.AuthenService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;

    @Value("${google.client.id}")
    private String clientId;

    @Value("${google.client.secret}")
    private String clientSecret;

    @Value("${google.redirect.uri}")
    private String redirectUri;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    AuthenService authenService;

    @Autowired
    ModelMapper modelMapper;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        Authentication auth;
        try {
            auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(auth);
            String username = auth.getName();
            String token = jwtTokenProvider.generateToken((CustomUserDetails) auth.getPrincipal());
            List<String> roles = new ArrayList<>();
            auth.getAuthorities().forEach(role -> roles.add(role.toString()));
            LoginResponse loginResponse = new LoginResponse(token, username, roles);
            return ResponseEntity.ok(loginResponse);
        }
        catch (BadCredentialsException e){
            throw new BadCredentialsException("Username or password is incorrect");
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@Valid @RequestBody SignupRequest signupRequest) throws ParseException {
        try {
            if (userRepository.findByUsername(signupRequest.getUsername()) != null)
                throw new RuntimeException("This username is already exsit");
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            User user = modelMapper.map(signupRequest, User.class);
            user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
            if (signupRequest.getBirthDate() != null)
                user.setBirthDate(format.parse(signupRequest.getBirthDate()));
            if (signupRequest.getRole().equals("ROLE_DOCTOR"))
                user.setRoles(Arrays.asList(roleRepository.findByCode("ROLE_USER"),roleRepository.findByCode("ROLE_DOCTOR")));
            else
                user.setRoles(Collections.singletonList(roleRepository.findByCode("ROLE_USER")));
            userRepository.save(user);
            StringResponse response = new StringResponse();
            response.setResponseCode(ResponseCode.SUCCESS.getCode());
            response.setResponseStatus(ResponseCode.SUCCESS.name());
            response.setMessage("Create User Successfully");
            return ResponseEntity.ok(response);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

//    http://localhost:8083/api/auth/loginGoogle?code=4%2F0AfJohXlX7nKM0fVrrv3264Oh-UOFU8pkAGETYTfkYdha9uuUp4EuOmxu5ygjuhroJKHB6w&scope=profile+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.profile
//    https://accounts.google.com/o/oauth2/auth?scope=profile&redirect_uri=http://localhost:8080/LoginGoogle/LoginGoogleHandler&response_type=code
//    &client_id=672564251488-nn8bqot6aiq2k96vrf8pn7ljqjgv19u3.apps.googleusercontent.com&approval_prompt=force
    @GetMapping("/loginGoogle")
    public ResponseEntity<?> loginWithGoogle(@RequestParam String code) {
        MultiValueMap<String, String> values = new LinkedMultiValueMap<>();
        values.add("code", code);
        values.add("client_id", clientId);
        values.add("client_secret", clientSecret);
        values.add("redirect_uri", redirectUri);
        values.add("grant_type", "authorization_code");

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(values, httpHeaders);
        ResponseEntity<ResponseTokenGoogleOAuth> response = restTemplate.postForEntity(
                "https://accounts.google.com/o/oauth2/token",
                request,
                ResponseTokenGoogleOAuth.class
        );

        ResponseTokenGoogleOAuth responseTokenGitHubOAuth = response.getBody();
        if (responseTokenGitHubOAuth != null) {
            String accessToken = responseTokenGitHubOAuth.getAccess_token();
            String idToken = responseTokenGitHubOAuth.getId_token();

            HttpHeaders userInfoHeaders = new HttpHeaders();
            userInfoHeaders.setBearerAuth(idToken);
            HttpEntity<Void> userInfoRequest = new HttpEntity<>(userInfoHeaders);
            ResponseEntity<UserGoogleResponse> userInfoResponse = restTemplate.exchange(
                    "https://www.googleapis.com/oauth2/v1/userinfo?access_token=" + accessToken,
                    HttpMethod.GET,
                    userInfoRequest,
                    UserGoogleResponse.class
            );

            UserGoogleResponse userGoogleResponse = userInfoResponse.getBody();
            User user = userRepository.findByUsername(userGoogleResponse.getName());
            if (user == null) {
                user.setUsername(userGoogleResponse.getName());
                user.setPassword(passwordEncoder.encode("abc123"));
                user.setRoles(Collections.singletonList(roleRepository.findByCode("ROLE_USER")));
                userRepository.save(user);
                // set info email của user
            }

            Authentication authentication=authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                    user.getUsername(),
                    user.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            LoginResponse loginResponse=new LoginResponse();
            loginResponse.setRoles(user.getRoles().stream().map(role -> role.getName()).collect(Collectors.toList()));
            loginResponse.setUsername(user.getUsername());
            loginResponse.setToken(jwtTokenProvider.generateToken(new CustomUserDetails(user)));
            return ResponseEntity.ok(loginResponse);
        }
        return null;
    }


    @PostMapping("/forgetPass")
    public ResponseEntity<?> forgetPass(@Valid @RequestBody ForgetPassRequest request){
        authenService.forgotPass(request);
        StringResponse response=new StringResponse();
        response.setMessage("Send code to your email successfully");
        response.setResponseCode(ResponseCode.SUCCESS.getCode());
        response.setResponseStatus(ResponseCode.SUCCESS.name());
        return ResponseEntity.ok(response);
    }

    // reset pass bằng token sau khi quên mật khẩu
    @PostMapping("/resetPass")
    public ResponseEntity<?> resetPass(@RequestBody ResetPassRequest request){
        authenService.resetPass(request);
        return ResponseEntity.ok("Reset Password Successfully!");
    }

    // đăng nhập bằng facebook
    // đăng nhập bằng github

}
