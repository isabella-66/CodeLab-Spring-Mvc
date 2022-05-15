package br.com.ifsp.regescweb.controllers;

import br.com.ifsp.regescweb.dto.RequisicaoNovoProfessor;
import br.com.ifsp.regescweb.models.Professor;
import br.com.ifsp.regescweb.models.StatusProfessor;
import br.com.ifsp.regescweb.repositories.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
public class ProfessorController {
    @Autowired //identifica dependência, cria o objeto e o injeta automaticamente (substitui o construtor)
    private ProfessorRepository professorRepository;

    //public ProfessorController(ProfessorRepository professorRepository) {
    //    this.professorRepository = professorRepository;
    //}

    @GetMapping("/professores") //sempre ao colocar método get + /professores, cai nessa action
    public ModelAndView index() {
        List<Professor> professores = this.professorRepository.findAll();

        ModelAndView mv = new ModelAndView("professores/index");
        mv.addObject("professores", professores);

        return mv;
    }

    @GetMapping("/professor/new")
    public ModelAndView nnew() {
        ModelAndView mv = new ModelAndView("professores/new");
        mv.addObject("statusProfessor", StatusProfessor.values()); //passagem dos valores de status
        return mv;
    }

    @PostMapping("/professores")
    public String create(@Valid RequisicaoNovoProfessor requisicao, BindingResult bindingResult) { //BindingResult é a resposta se está válido ou não
        if(bindingResult.hasErrors()) {
            System.out.println("\n********** TEM ERROS **********");
            return "redirect:/professor/new";
        } else {
            Professor professor = requisicao.toProfessor();
            this.professorRepository.save(professor); //inserção na base de dados
            return "redirect:/professores"; //redireciona para lista de professores pelo browser
        }
    }
}
