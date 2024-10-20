package br.laemcasa.api.controllers;

import br.laemcasa.api.domain.group.GroupRegisterDTO;
import br.laemcasa.api.domain.group.GroupResponseDTO;
import br.laemcasa.api.exceptions.TokenRequiredException;
import br.laemcasa.api.exceptions.UserNotFoundException;
import br.laemcasa.api.services.AuthenticationService;
import br.laemcasa.api.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/group")
public class GroupController {
    @Autowired
    private GroupService groupService;
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/new")
    public ResponseEntity<GroupResponseDTO> createGroup(@RequestHeader("Authorization") String token, @RequestBody GroupRegisterDTO data) {
        if (!token.contains("Bearer")) {
            throw new TokenRequiredException("Invalid or no token provided.");
        }

        var user = this.authenticationService.getUserByToken(token.replace("Bearer ", ""));

        if (user != null) {
            var group = this.groupService.createGroup(data, user);

            return ResponseEntity.ok(new GroupResponseDTO(group.getName(), group.getDescription(), user.getId()));
        }

        throw new UserNotFoundException("User not found.");
    }
}
