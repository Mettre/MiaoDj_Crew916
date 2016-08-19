package cn.chenhai.miaodj_monitor.model.entity;

/**
 * Created by ChenHai--霜华 on 2016/7/11. 17:17
 * 邮箱：248866527@qq.com
 */
public class UserEntity {

    /**
     * crew_name : 永晓
     * crew_headimg : /Uploads/images/201606/57738afe4a649.png
     * todo : 4
     */

    private PersonBean person;

    public PersonBean getPerson() {
        return person;
    }

    public void setPerson(PersonBean person) {
        this.person = person;
    }

    public static class PersonBean {
        private String crew_name;
        private String crew_headimg;
        private String todo;

        public String getCrew_name() {
            return crew_name;
        }

        public void setCrew_name(String crew_name) {
            this.crew_name = crew_name;
        }

        public String getCrew_headimg() {
            return crew_headimg;
        }

        public void setCrew_headimg(String crew_headimg) {
            this.crew_headimg = crew_headimg;
        }

        public String getTodo() {
            return todo;
        }

        public void setTodo(String todo) {
            this.todo = todo;
        }
    }
}
