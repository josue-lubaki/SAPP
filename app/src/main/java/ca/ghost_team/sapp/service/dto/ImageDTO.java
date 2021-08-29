package ca.ghost_team.sapp.service.dto;

public class ImageDTO {
    private boolean status;
    private String remarks;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        return "status=" + status + "\t" +
                ", remarks='" + remarks + '\'';
    }
}
