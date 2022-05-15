/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

/**
 *
 * @author shobha
 */
public class flash_news {

    private int id;
    private String message;
    private String status;
    private String links;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return beans.MyUtil.filterBad(message);
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return beans.MyUtil.filterBad(status);
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLinks() {
        return beans.MyUtil.filterBad(links);
    }

    public void setLinks(String links) {
        this.links = links;
    }
   
}
