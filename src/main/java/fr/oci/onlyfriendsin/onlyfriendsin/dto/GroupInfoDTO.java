package fr.oci.onlyfriendsin.onlyfriendsin.dto;

import fr.oci.onlyfriendsin.onlyfriendsin.domain.Group;
import lombok.Data;

@Data
public class GroupInfoDTO {

    private long groupId;
    private String groupName;
    private String ownerId;

    public GroupInfoDTO(Group group){
        this.groupId = group.getIdentifier();
        this.groupName = group.getName();
        this.ownerId = group.getOwner().getIdentifier();
    }

}