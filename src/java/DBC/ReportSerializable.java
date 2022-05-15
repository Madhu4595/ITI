/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DBC;

/**
 *
 * @author Gopi Daggumalle
 */
public class ReportSerializable implements java.io.Serializable {

    String statecode, dist_code, dist_name, itidist_code, seats_open, seats_fill;

    public String getSeats_open() {
        return seats_open;
    }

    public void setSeats_open(String seats_open) {
        this.seats_open = seats_open;
    }

    public String getSeats_fill() {
        return seats_fill;
    }

    public void setSeats_fill(String seats_fill) {
        this.seats_fill = seats_fill;
    }

    public String getStatecode() {

        return statecode;
    }

    public void setStatecode(String statecode) {

        this.statecode = statecode;
    }

    public String getDist_code() {

        return dist_code;
    }

    public void setDist_code(String dist_code) {

        this.dist_code = dist_code;
    }

    public String getDist_name() {

        return dist_name;
    }

    public void setDist_name(String dist_name) {

        this.dist_name = dist_name;
    }

    public String getItidist_code() {

        return itidist_code;
    }

    public void setItidist_code(String itidist_code) {

        this.itidist_code = itidist_code;
    }
}
