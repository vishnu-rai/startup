package stop.one.startup;

public class recycleritem {

    String Project_topic,Idea_topic,Idea_desc,Project_desc,Post_desc,Post_time,User_id,User_name,Post_title;

    public recycleritem(){}

    public recycleritem(String project_topic, String idea_topic, String idea_desc,
                        String project_desc, String post_desc, String post_time,
                        String user_id, String user_name, String post_title) {
        Project_topic = project_topic;
        Idea_topic = idea_topic;
        Idea_desc = idea_desc;
        Project_desc = project_desc;
        Post_desc = post_desc;
        Post_time = post_time;
        User_id = user_id;
        User_name = user_name;
        Post_title = post_title;
    }

    public String getPost_title() {
        return Post_title;
    }

    public void setPost_title(String post_title) {
        Post_title = post_title;
    }

    public String getPost_desc() {
        return Post_desc;
    }

    public void setPost_desc(String post_desc) {
        Post_desc = post_desc;
    }

    public String getPost_time() {
        return Post_time;
    }

    public void setPost_time(String post_time) {
        Post_time = post_time;
    }

    public String getUser_id() {
        return User_id;
    }

    public void setUser_id(String user_id) {
        User_id = user_id;
    }

    public String getUser_name() {
        return User_name;
    }

    public void setUser_name(String user_name) {
        User_name = user_name;
    }

    public String getProject_topic() {
        return Project_topic;
    }

    public void setProject_topic(String project_topic) {
        Project_topic = project_topic;
    }

    public String getIdea_topic() {
        return Idea_topic;
    }

    public void setIdea_topic(String idea_topic) {
        Idea_topic = idea_topic;
    }

    public String getIdea_desc() {
        return Idea_desc;
    }

    public void setIdea_desc(String idea_desc) {
        Idea_desc = idea_desc;
    }

    public String getProject_desc() {
        return Project_desc;
    }

    public void setProject_desc(String project_desc) {
        Project_desc = project_desc;
    }
}
