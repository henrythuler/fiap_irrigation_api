package br.com.fiap.irrigationapi.modules.users.controllers;

import br.com.fiap.irrigationapi.config.TokenService;
import br.com.fiap.irrigationapi.modules.users.dtos.*;
import br.com.fiap.irrigationapi.modules.users.models.User;
import br.com.fiap.irrigationapi.modules.users.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid LoginInput loginInput) {

        var usernamePassword = new UsernamePasswordAuthenticationToken(
                loginInput.email(),
                loginInput.password()
        );

        var auth = authenticationManager.authenticate(usernamePassword);
        var user = (User)auth.getPrincipal();

        var token = tokenService.generateToken(user);
        return ResponseEntity.ok(new LoginOutput(token));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserOutput register(@RequestBody @Valid UserRegisterInput userRegisterInput) {
        return service.save(userRegisterInput);
    }

    @PutMapping("/activate/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity activate(@PathVariable Long id) {
        var user = service.activate(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/deactivate/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity deactivate(@PathVariable Long id) {
        var user = service.deactivate(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/update-info")
    @ResponseStatus(HttpStatus.CREATED)
    public UserOutput updateInfo(@RequestBody @Valid UserInfoUpdateInput userInfoUpdateInput) {

        var user = service.findById(userInfoUpdateInput.id());
        user.setName(userInfoUpdateInput.name());
        user.setEmail(userInfoUpdateInput.email());
        user.setPassword(userInfoUpdateInput.password());

        return service.update(user);
    }

    @PutMapping("/upgrade-permission/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity upgradePermission(@PathVariable Long id) {
        var user = service.upgradePermission(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/downgrade-permission/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity downgradePermission(@PathVariable Long id) {
        var user = service.downgradePermission(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserOutput getById(@PathVariable Long id) {
        return new UserOutput(service.findById(id));
    }

    @GetMapping("/email/{email}")
    @ResponseStatus(HttpStatus.OK)
    public UserOutput getByEmail(@PathVariable String email) {
        return new UserOutput(service.findByEmail(email));
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public Page<UserOutput> getAll(Pageable page) {
        return service.getAll(page);
    }

}
