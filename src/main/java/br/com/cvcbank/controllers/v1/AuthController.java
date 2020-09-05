package br.com.cvcbank.controllers.v1;

import br.com.cvcbank.configurations.security.models.Credentials;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@AllArgsConstructor
public class AuthController {
    /**
     * Swagger documentation of login feature
     *
     * @param credentials
     */
    @ApiOperation("Login.")
    @PostMapping
    public void login(@RequestBody Credentials credentials) {
        throw new IllegalStateException("This method shouldn't be called. It's implemented by Spring Security filters.");
    }
}
