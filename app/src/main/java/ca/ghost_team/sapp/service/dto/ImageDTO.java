package ca.ghost_team.sapp.service.dto;

public class ImageDTO {
    private boolean status;
    private String remarks;
    private long idImage;

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

    public long getIdImage() {
        return idImage;
    }

    public void setIdImage(long idImage) {
        this.idImage = idImage;
    }

    @Override
    public String toString() {
        return "ImageDTO{" +
                "status=" + status +
                ", remarks='" + remarks + '\'' +
                ", idImage=" + idImage +
                '}';
    }
}
