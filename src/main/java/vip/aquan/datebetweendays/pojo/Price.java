package vip.aquan.datebetweendays.pojo;

/**
 * @author: wcp
 * @date: 2020/11/18 21:37
 * @Description:
 */
public class Price {
    private String mydate;
    private Integer totle;

    public Price(String mydate, Integer totle) {
        this.mydate = mydate;
        this.totle = totle;
    }

    @Override
    public String toString() {
        return "Price{" +
                "mydate='" + mydate + '\'' +
                ", totle=" + totle +
                '}';
    }

    public String getMydate() {
        return mydate;
    }

    public void setMydate(String mydate) {
        this.mydate = mydate;
    }

    public Integer getTotle() {
        return totle;
    }

    public void setTotle(Integer totle) {
        this.totle = totle;
    }
}
