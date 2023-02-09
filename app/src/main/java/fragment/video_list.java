package fragment;

public class video_list {
    private float number;
    private String name;
    private String small_name;
    private String url;
    private int id;

    public video_list(int id,String url,String name,String small_name,float number){
        this.id = id;
        this.url = url;
        this.name = name;
        this.small_name = small_name;
        this.number = number;
    }

    public String toString(){
        return String.valueOf(this.id)+String.valueOf(this.url)+String.valueOf(this.name)+String.valueOf(this.small_name)+String.valueOf(this.number);
    }

    public int getId(){
        return id;
    }

    public float getNumber(){
        return number;
    }

    public String getName(){
        return name;
    }

    public String getSmall_name(){
        return small_name;
    }

    public String getUrl(){
        return url;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setNumber(float number){
        this.number = number;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setSmall_name(String small_name){
        this.small_name = small_name;
    }

    public void setUrl(String url){
        this.url = url;
    }
}
