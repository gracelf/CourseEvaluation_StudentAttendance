package com.finalproject.courseevaluation_studentattendance.Controller;

import com.finalproject.courseevaluation_studentattendance.Model.Course;
import com.finalproject.courseevaluation_studentattendance.Model.Evaluation;
import com.finalproject.courseevaluation_studentattendance.Repositories.CourseRepository;
import com.finalproject.courseevaluation_studentattendance.Repositories.EvaluationRepository;
import com.finalproject.courseevaluation_studentattendance.Services.CourseService;
import com.finalproject.courseevaluation_studentattendance.Services.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.Date;

@Controller
@RequestMapping("/eval")
public class EvalController {
    @Autowired
    EvaluationRepository evaluationRepository;

    @Autowired
    CourseService courseService;

    @Autowired
    EvaluationService evaluationService;

    @Autowired
    CourseRepository courseRepository;



    @RequestMapping("/home")
    public String evalHome(){
        return "/evalpages/evalhome";
    }

//
    @GetMapping("/evaluationentry/{courseid}")
    public String getEvaluation(@PathVariable("courseid") long id,  Model model) {

        Evaluation ev =  new Evaluation();
        ev.setCourseEvaluation(courseRepository.findOne(id));

        System.out.println("Course Id is"+ id);

        model.addAttribute("courseId",id);
        model.addAttribute("neweval", ev);

        return "evalpages/evaluationentry";

    }

    @PostMapping("/evaluationentry/{courseId}")
    public String entrypost(@ModelAttribute("neweval") Evaluation eval,@PathVariable("courseId")long courseId, Model model)
    {
        Course cr=courseRepository.findOne(courseId);
        evaluationService.addEvalToCourse(eval,cr);
        return "evalpages/confirmeval";
    }


    @GetMapping("/searchcourse")
    public String searchCourse()
    {

        return "evalpages/searchcourse";
    }

    @PostMapping("/searchcourse")
    public String searchCoursePost(@RequestParam("crnfield")long crn, Model model,Course course )
    {
        model.addAttribute("searcheval", courseRepository.findByCrn(crn));
        return "evalpages/searchresult";
    }

//    @GetMapping("/evaluation/{id}")
//    public String getEvaluation(@PathParam("id")Long id, Model model)
//    {
//
//        Course course = courseRepository.findOne(id);
//
//        Evaluation evalforaCourse = new Evaluation();
//
//        evalforaCourse.setCourseEvaluation(course);
//
//      //  model.addAttribute("course", course);
//
//        model.addAttribute("neweval", evalforaCourse);
//
//        return "evalpages/evaluation";
//    }
//
//    @RequestMapping("/evaluation/{id}")
//    public String postEvaluation(@PathVariable("id") long id,Evaluation evaluation, Model model)
//    {
//
//
//        model.addAttribute("neweval", evaluation);
//        evaluationRepository.save(evaluation);
//        return "evaluation";
//    }

//    @GetMapping("/evaluation/{id}")
//    public String getEvaluation(@PathParam("id")Long id, Model model)
//    {
//        model.addAttribute("neweval", new Evaluation());
//        return "/evalpages/evaluation";
//    }
//    @RequestMapping("/evaluation/{id}")
//    public String postEvaluation(@PathParam("id")Long id,Evaluation evaluation, Model model)
//    {
//
////        String testcrn = null;
////
////        courseService.findByCRN(testcrn);
//        model.addAttribute("neweval", evaluation);
//        evaluationRepository.save(evaluation);
//        return "/evalpages/evaluation";
//    }


     //search by crn
//   @GetMapping("searchcrn")
//    public String getSearchCRN(@RequestParam("crn") long crn , Model model)
//   {
//
//       model.addAttribute("crn", new Course());
//       model.addAttribute("crn",courseService.findByCRN(crn));
//       return "searchcrn";
//   }
//
//
//   @PostMapping("searchcrn")
//   public String postSearchCRN(@RequestParam("crn")long crn, Model model, Evaluation eval, Course cr)
//   {
//      evaluationService.addEvalToCourse(cr, eval);
//     // evaluationService.SaveEntry(eval);
//       return "searchresult";
//   }


   // search by date

    @RequestMapping("/searchbydate")
    public String serachByDate(@PathVariable("byDate")Date startDate,  Evaluation eval, Model model, Course course)
    {
        model.addAttribute("bydate", new Course());
        model.addAttribute("bydate", courseService.findByStartDate(startDate.toString()));

       return "serachbydate";
    }

    @PostMapping("/serachbydate")
    public String postSearchByDate(@RequestParam("bydate") Date startDate, Evaluation evaluation, Model model,Course course)
    {
        return "searchresult";
    }


    // add course crn to evaluation




}
