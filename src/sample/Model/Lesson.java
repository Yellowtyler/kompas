package sample.Model;


import javafx.beans.property.SimpleStringProperty;

import java.util.Comparator;

public class Lesson {


    private SimpleStringProperty subject;
    private SimpleStringProperty type;
    private SimpleStringProperty teacher;
    private SimpleStringProperty auditory;
    private SimpleStringProperty lessonNumber;
    private SimpleStringProperty day;
    private SimpleStringProperty week;
    public Lesson() {
        day = new SimpleStringProperty();
        type = new SimpleStringProperty();
        teacher= new SimpleStringProperty();
        auditory = new SimpleStringProperty();
        lessonNumber = new SimpleStringProperty();
        subject = new SimpleStringProperty();
        week = new SimpleStringProperty();
    }



    public static class SortByDay implements Comparator<Lesson> {

        public int compare(Lesson o1, Lesson o2) {
            String[]    days = {"Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота"};
          Integer k1=0;
           Integer k2=0;
            for(int i=0;i<days.length;i++)
            {
                if(days[i]==o1.getDay())
                {
                    k1=i;
                }
                if(days[i]==o2.getDay())
                {
                    k2=i;
                }
            }
            if(k1==k2)
            {
                return o1.getLessonNumber().compareTo(o2.getLessonNumber());
            }
        else  return k1.compareTo(k2);
        }

    }


    public boolean equals1(Lesson o) {
        if(o.getLessonNumber().equals(this.getLessonNumber())&&o.getDay().equals(this.getDay())&&o.getAuditory().equals(this.getAuditory())&&(o.getSubject().contains(this.getSubject()) || this.getSubject().contains(o.getSubject()))&&o.getTeacher().equals(this.getTeacher())&&o.getType().equals(this.getType())&&o.getWeek().equals(this.getWeek()))
        {
            return true;
        }
        else return false;
    }




    public String getSubject() {
        return subject.get();
    }

    public SimpleStringProperty subjectProperty() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject.set(subject);
    }

    public String getType() {
        return type.get();
    }

    public SimpleStringProperty typeProperty() {
        return type;
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public String getTeacher() {
        return teacher.get();
    }

    public SimpleStringProperty teacherProperty() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher.set(teacher);
    }

    public String getAuditory() {
        return auditory.get();
    }

    public SimpleStringProperty auditoryProperty() {
        return auditory;
    }

    public void setAuditory(String auditory) {
        this.auditory.set(auditory);
    }

    public String getLessonNumber() {
        return lessonNumber.get();
    }


    public void setLessonNumber(String lessonNumber) {
        this.lessonNumber.set(lessonNumber);
    }

    public String getDay() {
        return day.get();
    }

    public SimpleStringProperty dayProperty() {
        return day;
    }

    public void setDay(String day1) {
      day.set(day1);
    }

    public String getWeek() {
        return week.get();
    }

    public SimpleStringProperty weekProperty() {
        return week;
    }

    public void setWeek(String week) {
        this.week.set(week);
    }




}
