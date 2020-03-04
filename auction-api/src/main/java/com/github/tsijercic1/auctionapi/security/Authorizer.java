package com.github.tsijercic1.auctionapi.security;

import com.github.tsijercic1.auctionapi.exceptions.UnauthorizedException;

public class Authorizer {
    public static void validateAuthority(UserPrincipal userPrincipal, String role) {
        if (role.equals("ANONYMOUS") && userPrincipal == null) {
            return;
        }
        if (userPrincipal != null && userPrincipal.getAuthorities().stream().anyMatch(auth -> auth.toString().equals(role))) {
            return;
        }
        throw new UnauthorizedException("Unauthorized");
    }
}
