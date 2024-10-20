package br.laemcasa.api.repositories;

import br.laemcasa.api.domain.group.Group;
import br.laemcasa.api.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;
import java.util.UUID;

public interface GroupRepository extends JpaRepository<Group, UUID> {
    Set<Group> findGroupByOwner(User Owner);
}
