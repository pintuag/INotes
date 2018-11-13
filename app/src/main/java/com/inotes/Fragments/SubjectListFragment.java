package com.inotes.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.inotes.Adapters.SemesterAdapter;
import com.inotes.Adapters.SubjectAdapter;
import com.inotes.Models.NotesName;
import com.inotes.Models.Subjects;
import com.inotes.R;
import com.inotes.SharedPref.SessionManager;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.Subject;

public class SubjectListFragment extends Fragment {

    SessionManager manager;
    RecyclerView recyclerView2;
    FirebaseStorage firebaseStorage;
    DatabaseReference databaseReference;
    List<Subjects> list = new ArrayList<>();
    SubjectAdapter adapter;
    ProgressBar progressBar;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.semester_fragment,container,false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Select Subjects");

        manager=new SessionManager();
        progressBar =(ProgressBar)view.findViewById(R.id.progressbar);
        String semnum = getArguments().getString("semnum");
        String sem=getArguments().getString("sem");
        Log.e("subjects",""+semnum);

        recyclerView2 = (RecyclerView) view.findViewById(R.id.semesterRecy);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView2.setItemAnimator(new DefaultItemAnimator());
        if(manager.getPrefs(getActivity(),"course").equals("bca")){

            if(semnum.equals("1")) {
                bcasemester1();
            }else  if(semnum.equals("2")) {
                bcasemester2();
            }else  if(semnum.equals("3")) {
                bcasemester3();
            }else  if(semnum.equals("4")) {
                bcasemester4();
            }else  if(semnum.equals("5")) {
                bcasemester5();
            }else  if(semnum.equals("6")) {
                bcasemester6();
            }
        }else if(manager.getPrefs(getActivity(),"course").equals("bba")){

            if(semnum.equals("1")) {
                bbasemester1();
            }else  if(semnum.equals("2")) {
                bbasemester2();
            }else  if(semnum.equals("3")) {
                bbasemester3();
            }else  if(semnum.equals("4")) {
                bbasemester4();
            }else  if(semnum.equals("5")) {
                bbasemester5();
            }else  if(semnum.equals("6")) {
                bbasemester6();
            }
        }else if(manager.getPrefs(getActivity(),"course").equals("bjmc")){

            if(semnum.equals("1")) {
                bjmcsemester1();
            }else  if(semnum.equals("2")) {
                bjmcsemester2();
            }else  if(semnum.equals("3")) {
                bjmcsemester3();
            }else  if(semnum.equals("4")) {
                bjmcsemester4();
            }else  if(semnum.equals("5")) {
                bjmcsemester5();
            }else  if(semnum.equals("6")) {
                bjmcsemester6();
            }
        }




        adapter = new SubjectAdapter(getActivity(),list,sem,getActivity().getSupportFragmentManager());
        recyclerView2.setAdapter(adapter);
       // recyclerView2.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

        return view;
    }

    //bca subjects
    private void bcasemester1() {
        String[] sub = new String[]{"Mathematics–I",
                "Technical Communication",
                "Introduction to Programming Language using C",
                "Introduction to Computers &IT",
                "Physics"};
        String[] code = new String[]{"BCA 101",
                "BCA 103",
                "BCA 105",
                "BCA 107",
                "BCA 109"};
        Log.e("Size of string"," g "+code.length);
        for(int i =0;i<5;i++){
            Subjects subjects = new Subjects();
            subjects.setCoursecode(code[i]);
            subjects.setSubjects(sub[i]);
            list.add(subjects);
        }
    }

    private void bcasemester2() {
        String[] sub = new String[]{"Mathematics–II",
                "Principles of Management",
                "Digital Electronics",
                "Data Structure Using C",
                "Database Management System"};
        String[] code = new String[]{"BCA 102",
                "BCA 104",
                "BCA 106",
                "BCA 108",
                "BCA 110"};
        for(int i =0;i<5;i++){
            Subjects subjects = new Subjects();
            subjects.setCoursecode(code[i]);
            subjects.setSubjects(sub[i]);
            list.add(subjects);
        }

    }

    private void bcasemester3() {
        String[] sub = new String[]{"Mathematics–III",
                "Computer Architecture",
                "Front End Design Tool VB.Net",
                "Principles of Accounting",
                "Object Oriented Programming using C++"};
        String[] code = new String[]{"BCA 201",
                "BCA 203",
                "BCA 205",
                "BCA 207",
                "BCA 209"};
        for(int i =0;i<5;i++){
            Subjects subjects = new Subjects();
            subjects.setCoursecode(code[i]);
            subjects.setSubjects(sub[i]);
            list.add(subjects);
        }

    }

    private void bcasemester4() {
        String[] sub = new String[]{"Mathematics–IV",
                "Web Technologies",
                "Java Programming",
                "Software Engineering",
                "Computer Networks"};
        String[] code = new String[]{"BCA 202",
                "BCA 204",
                "BCA 206",
                "BCA 208",
                "BCA 210"};
        for(int i =0;i<5;i++){
            Subjects subjects = new Subjects();
            subjects.setCoursecode(code[i]);
            subjects.setSubjects(sub[i]);
            list.add(subjects);
        }

    }

    private void bcasemester5() {
        String[] sub = new String[]{"Operating System",
                "Computer Graphics",
                "E-Commerce",
                "Software Testing",
                "Microprocessor",
                "Advance Computer Networks",
                "Web Based Programming",
                "Business Economics"};
        String[] code = new String[]{"BCA 301",
                "BCA 303",
                "BCA 305",
                "BCA 307",
                "BCA 309",
                "BCA 311",
                "BCA 313",
                "BCA 315"};
        for(int i =0;i<8;i++){
            Subjects subjects = new Subjects();
            subjects.setCoursecode(code[i]);
            subjects.setSubjects(sub[i]);
            list.add(subjects);
        }

    }

    private void bcasemester6() {
        String[] sub = new String[]{"Data Ware Housing & Data Mining",
                "Mobile Computing",
                "Linux Environment",
                "Multimedia & Its Applications",
                "Bio Informatics",
                "Artificial Intelligence",
                "Network Security",
                "Network Programming"};
        String[] code = new String[]{"BCA 302",
                "BCA 304",
                "BCA 306",
                "BCA 308",
                "BCA 310",
                "BCA 312",
                "BCA 314",
                "BCA 316"};
        for(int i =0;i<8;i++){
            Subjects subjects = new Subjects();
            subjects.setCoursecode(code[i]);
            subjects.setSubjects(sub[i]);
            list.add(subjects);
        }

    }


    //bba subjects
    private void bbasemester1() {
        String[] sub = new String[]{"Principles of Management",
                "Business Economics-I",
                "Business Mathematics",
                "Computer Fundamentals",
                "Financial Accounting",
                "Personality Development & Communication Skills-I"};
        String[] code = new String[]{"BBA 101",
                "BBA 103",
                "BBA 105",
                "BBA 107",
                "BBA 109",
                "BBA 111"};
        Log.e("Size of string"," g "+code.length);
        for(int i =0;i<6;i++){
            Subjects subjects = new Subjects();
            subjects.setCoursecode(code[i]);
            subjects.setSubjects(sub[i]);
            list.add(subjects);
        }
    }

    private void bbasemester2() {
        String[] sub = new String[]{"Business Organization",
                "Business Economics-II",
                "Quantitative Techniques & Operations Research in Management",
                "Data Base Management System",
                "Cost Accounting",
                "Personality Development & Communication Skills-II "};
        String[] code = new String[]{"BBA 102",
                "BBA 104",
                "BBA 106",
                "BBA 108",
                "BBA 110",
                "BBA 112"};
        for(int i =0;i<6;i++){
            Subjects subjects = new Subjects();
            subjects.setCoursecode(code[i]);
            subjects.setSubjects(sub[i]);
            list.add(subjects);
        }

    }

    private void bbasemester3() {
        String[] sub = new String[]{"Organizational Behaviour",
                "Indian Economy",
                "Marketing Management",
                "Computer Applications-I",
                "Management Accounting",
                "Personality Development & Communication Skills-III (Minor Project Report)"};
        String[] code = new String[]{"BBA 201",
                "BBA 203",
                "BBA 205",
                "BBA 207",
                "BBA 209",
                "BBA 211"};
        for(int i =0;i<6;i++){
            Subjects subjects = new Subjects();
            subjects.setCoursecode(code[i]);
            subjects.setSubjects(sub[i]);
            list.add(subjects);
        }

    }

    private void bbasemester4() {
        String[] sub = new String[]{"Human Resource Management",
                "Business Environment",
                "Marketing Research",
                "Computer Application-II",
                "Business Laws",
                "Taxation Laws"};
        String[] code = new String[]{"BBA 202",
                "BBA 204",
                "BBA 206",
                "BBA 208",
                "BBA 210",
                "BBA 212"};
        for(int i =0;i<6;i++){
            Subjects subjects = new Subjects();
            subjects.setCoursecode(code[i]);
            subjects.setSubjects(sub[i]);
            list.add(subjects);
        }

    }

    private void bbasemester5() {
        String[] sub = new String[]{"Values & Ethics in Business",
                "Marketing Management-II",
                "Production & Operations Management ",
                "Management Information System",
                "Financial Management",
                "Summer Training Report & Viva Voce "};
        String[] code = new String[]{"BBA 301",
                "BBA 303",
                "BBA 305",
                "BBA 307",
                "BBA 309",
                "BBA 311"};
        for(int i =0;i<6;i++){
            Subjects subjects = new Subjects();
            subjects.setCoursecode(code[i]);
            subjects.setSubjects(sub[i]);
            list.add(subjects);
        }

    }

    private void bbasemester6() {
        String[] sub = new String[]{"Business Policy & Strategy",
                "Project Planning & Evaluation",
                "Entrepreneurship Development",
                "International Business Management",
                "Project Report and Viva-Voce",
                "Environmental Science"};
        String[] code = new String[]{"BBA 302",
                "BBA 304",
                "BBA 306",
                "BBA 308",
                "BBA 310",
                "BBA 312"};
        for(int i =0;i<6;i++){
            Subjects subjects = new Subjects();
            subjects.setCoursecode(code[i]);
            subjects.setSubjects(sub[i]);
            list.add(subjects);
        }

    }


    //bjmc subjects
    private void bjmcsemester1() {
        String[] sub = new String[]{"Writing for Media",
                "Socio-Economic & Political Scenario",
                "Introduction to Communication",
                "Basics of Design & Graphics",
                "Indian Culture"};
        String[] code = new String[]{"BJMC 101",
                "BJMC 103",
                "BJMC 105",
                "BJMC 107",
                "BJMC 109"};
        Log.e("Size of string"," g "+code.length);
        for(int i =0;i<5;i++){
            Subjects subjects = new Subjects();
            subjects.setCoursecode(code[i]);
            subjects.setSubjects(sub[i]);
            list.add(subjects);
        }
    }

    private void bjmcsemester2() {
        String[] sub = new String[]{"History of Print & Broadcasting in India",
                "Print Journalism-I",
                "Media Laws & Ethics",
                "Still Photography",
                "Cost Accounting"};
        String[] code = new String[]{"BJMC 102",
                "BJMC 104",
                "BJMC 106",
                "BJMC 108"};
        for(int i =0;i<4;i++){
            Subjects subjects = new Subjects();
            subjects.setCoursecode(code[i]);
            subjects.setSubjects(sub[i]);
            list.add(subjects);
        }

    }

    private void bjmcsemester3() {
        String[] sub = new String[]{"Development & Communication",
                "Print Journalism-II",
                "Radio Journalism &   Production",
                "Basics of Camera, Lights & Sound",
                "Summer Training Report"};
        String[] code = new String[]{"BJMC 201",
                "BJMC 203",
                "BJMC 205",
                "BJMC 207",
                "BJMC 209"};
        for(int i =0;i<5;i++){
            Subjects subjects = new Subjects();
            subjects.setCoursecode(code[i]);
            subjects.setSubjects(sub[i]);
            list.add(subjects);
        }

    }

    private void bjmcsemester4() {
        String[] sub = new String[]{"Television Journalism &   Production",
                "Introduction to Advertising",
                "Public Relations",
                "New Media"};
        String[] code = new String[]{"BJMC 202",
                "BJMC 204",
                "BJMC 206",
                "BJMC 208"};
        for(int i =0;i<4;i++){
            Subjects subjects = new Subjects();
            subjects.setCoursecode(code[i]);
            subjects.setSubjects(sub[i]);
            list.add(subjects);
        }

    }

    private void bjmcsemester5() {
        String[] sub = new String[]{"Advertising Practices",
                "Event Management: Principles & Method",
                "Media Research",
                "Environment Communication ",
                "Functional Exposure Report"};
        String[] code = new String[]{"BJMC 301",
                "BJMC 303",
                "BJMC 305",
                "BJMC 307",
                "BJMC 309"};
        for(int i =0;i<5;i++){
            Subjects subjects = new Subjects();
            subjects.setCoursecode(code[i]);
            subjects.setSubjects(sub[i]);
            list.add(subjects);
        }

    }

    private void bjmcsemester6() {
        String[] sub = new String[]{"Media Organisation & Management",
                "Contemporary Issues ",
                "Global Media Scenario"};
        String[] code = new String[]{"BJMC 302",
                "BJMC 304",
                "BJMC 306"};
        for(int i =0;i<3;i++){
            Subjects subjects = new Subjects();
            subjects.setCoursecode(code[i]);
            subjects.setSubjects(sub[i]);
            list.add(subjects);
        }

    }





    @Override
    public void onResume() {
        super.onResume();

    }


}
