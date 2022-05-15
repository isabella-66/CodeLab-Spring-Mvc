package br.com.ifsp.regescweb.controllers;

import br.com.ifsp.regescweb.dto.RequisicaoNovoProfessor;
import br.com.ifsp.regescweb.models.Professor;
import br.com.ifsp.regescweb.models.StatusProfessor;
import br.com.ifsp.regescweb.repositories.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class ProfessorController {
    @Autowired //identifica dependência, cria o objeto e o injeta automaticamente (substitui o construtor)
    private ProfessorRepository professorRepository;

    @GetMapping("/professores") //sempre ao colocar método get + /professores, cai nessa action
    public ModelAndView index() {
        List<Professor> professores = this.professorRepository.findAll();

        ModelAndView mv = new ModelAndView("professores/index");
        mv.addObject("professores", professores);

        return mv;
    }

    @GetMapping("/professores/new")
    public ModelAndView nnew(RequisicaoNovoProfessor requisicao) {
        ModelAndView mv = new ModelAndView("professores/new");
        mv.addObject("listaStatusProfessor", StatusProfessor.values()); //passagem dos valores de status
        return mv;
    }

    @PostMapping("/professores")
    public ModelAndView create(@Valid RequisicaoNovoProfessor requisicao, BindingResult bindingResult) { //BindingResult é a resposta se está válido ou não
        if(bindingResult.hasErrors()) {
            System.out.println("\n*********** TEM ERROS **************\n");
            ModelAndView mv = new ModelAndView("/professores/new");
            mv.addObject("listaStatusProfessor", StatusProfessor.values());
            return mv;
        } else {
            Professor professor = requisicao.toProfessor();
            this.professorRepository.save(professor); //inserção na base de dados
            return new ModelAndView("redirect:/professores/" + professor.getId()); //redireciona para lista de professores pelo browser
        }
    }

    @GetMapping("/professores/{id}")
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
}
