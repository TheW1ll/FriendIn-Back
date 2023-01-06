package fr.oci.onlyfriendsin.onlyfriendsin.dto;

import lombok.Data;

@Data
public class GroupCreationResponseDTO {
    private final boolean isGroupCreated;
    private final long groupId;
}
