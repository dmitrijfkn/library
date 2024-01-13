package com.pet.library.configurations;

import com.pet.library.entities.User;
import com.pet.library.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
public class UserSecurity implements AuthorizationManager<RequestAuthorizationContext> {

    private final RoleService roleService;

    @Autowired
    public UserSecurity(RoleService roleService) {
        this.roleService = roleService;
    }


    @Override
    public AuthorizationDecision check(Supplier authenticationSupplier, RequestAuthorizationContext ctx) {
        // get {userId} from the request
        Long userId = Long.parseLong(ctx.getVariables().get("userId"));
        Authentication authentication = (Authentication) authenticationSupplier.get();
        return new AuthorizationDecision(hasUserIdOrLibrarian(authentication, userId));
    }

    public boolean hasUserIdOrLibrarian(Authentication authentication, Long userId) {
        return !authentication.getPrincipal().equals("anonymousUser")
                && authentication.isAuthenticated()
                && (
                ((User) authentication.getPrincipal()).getId().equals(userId)
                        || roleService.isLibrarianOrAdmin((User) authentication.getPrincipal()));
    }


}