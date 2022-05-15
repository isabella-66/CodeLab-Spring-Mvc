package br.com.ifsp.regescweb.controllers;

import br.com.ifsp.regescweb.dto.RequisicaoFormProfessor;
import br.com.ifsp.regescweb.models.Professor;
import br.com.ifsp.regescweb.models.StatusProfessor;
import br.com.ifsp.regescweb.repositories.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/professores") //sempre mapeia professores
public class ProfessorController {
    @Autowired //identifica dependência, cria o objeto e o injeta automaticamente (substitui o construtor)
    private ProfessorRepository professorRepository;

    @GetMapping("") //sempre ao colocar método get + /professores, cai nessa action
    public ModelAndView index() {
        List<Professor> professores = this.professorRepository.findAll();

        ModelAndView mv = new ModelAndView("professores/index");
        mv.addObject("professores", professores);

        return mv;
    }

    @GetMapping("/new")
    public ModelAndView nnew(RequisicaoFormProfessor requisicao) {
        ModelAndView mv = new ModelAndView("professores/new");
        mv.addObject("listaStatusProfessor", StatusProfessor.values()); //passagem dos valores de status
        return mv;
    }

    @PostMapping("")
    public ModelAndView create(@Valid RequisicaoFormProfessor requisicao, BindingResult bindingResult) { //BindingResult é a resposta se está válido ou não
        if(bindingResult.hasErrors()) {
            System.out.println("\n*********** TEM ERROS **************\n");
            ModelAndView mv = new ModelAndView("/professores/new");
            mv.addObject("listaStatusProfessor", StatusProfessor.values());
            return mv;
        } else {
            Professor professor = requisicao.toProfessor();
            this.professorRepository.save(professor); //inserção na base de dados
            return new ModelAndView("redirect:/professores/new" + professor.getId()); //redireciona para lista de professores pelo browser
        }
    }

    @GetMapping("/{id}")
    public ModelAndView show(@PathVariable Long id) { //variável definida na URL
        Optional<Professor> optional = this.professorRepository.findById(id); //Optional para não correr o risco de passar dados vazios

        if (optional.isPresent()) {
            Professor professor = optional.get();

            ModelAndView mv = new ModelAndView("professores/show");
            mv.addObject("professor", professor);
            return mv;
        } else { //não achou registro na tabela Professor com o id informado
            System.out.println("$$$$$ NÃO ACHOU O PROFESSOR DE ID " + id + "");
            return new ModelAndView("redirect:/professores");
        }
    }

    @GetMapping("/{id}/edit")
    public ModelAndView edit(@PathVariable Long id, RequisicaoFormProfessor requisicao) {
        Optional<Professor> optional = this.professorRepository.findById(id);

        if (optional.isPresent()) {
            Professor professor = optional.get();
            requisicao.fromProfessor(professor);

            ModelAndView mv = new ModelAndView("/professores/edit");
            mv.addObject("professorId", professor.getId());
            mv.addObject("listaStatusProfessor", StatusProfessor.values());

            return mv;
        } else {
            System.out.println("$$$$$ NÃO ACHOU O PROFESSOR DE ID " + id + "");
            return new ModelAndView("redirect:/professores");
        }
    }

    @PostMapping("/{id}")
    public ModelAndView update(@PathVariable Long id, @Valid RequisicaoFormProfessor requisicao, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ModelAndView mv = new ModelAndView("professores/edit");
            mv.addObject("professorId", id);
            mv.addObject("listaStatusProfessor", StatusProfessor.values());
            return mv;
        } else {
            Optional<Professor> optional = this.professorRepository.findById(id);

            if (optional.isPresent()) {
                Professor professor = requisicao.toProfessor(optional.get());
                this.professorRepository.save(professor);
                return new ModelAndView("redirect:/professores/" + professor.getId());
            } else {
                System.out.println("$$$$$ NÃO ACHOU O PROFESSOR DE ID " + id + "");
                return new ModelAndView("redirect:/professores");
            }
        }
    }
}
