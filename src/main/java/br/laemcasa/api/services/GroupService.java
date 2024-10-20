package br.laemcasa.api.services;

import br.laemcasa.api.domain.group.Group;
import br.laemcasa.api.domain.group.GroupRegisterDTO;
import br.laemcasa.api.domain.user.User;
import br.laemcasa.api.repositories.GroupRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupService {
    @Autowired
    private GroupRepository groupRepository;

    @Transactional
    public Group createGroup(GroupRegisterDTO data, User owner) {
        if (owner != null) {
            Group group = new Group(data.name(), data.description(), owner);
            group.getMembers().add(owner);

            return this.groupRepository.save(group);
        }

        throw new RuntimeException("Error during group registration");
    }
}
