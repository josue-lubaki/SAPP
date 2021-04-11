package ca.ghost_team.sapp.service.dto;


import ca.ghost_team.sapp.model.Message;

public class FcmMessage {
    private final String to = "/topics/mychat";
    public Message data;

}
