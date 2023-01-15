package fr.oci.onlyfriendsin.onlyfriendsin.dto;

import lombok.Data;

@Data
public class GroupOwnerDTO {
    private final boolean groupExists;

    private final String groupOwnerId;
}
