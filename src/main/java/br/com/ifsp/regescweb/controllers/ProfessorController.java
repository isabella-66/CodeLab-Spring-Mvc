package br.com.ifsp.regescweb.controllers;

import br.com.ifsp.regescweb.models.Professor;
import br.com.ifsp.regescweb.models.StatusProfessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Controller
public class ProfessorController {

    @GetMapping("/professores") //sempre ao colocar método get + /professores, cai nessa action
    public ModelAndView index() {
        Professor charlotteVanderwall = new Professor("Charlotte Vanderwall", new BigDecimal(5000.0), StatusProfessor.ATIVO);
        charlotteVanderwall.setId(1L);
        Professor xavierOsawski = new Professor("Xavier Osawski", new BigDecimal(10000.0), StatusProfessor.APOSENTADO);
        xavierOsawski.setId(2L);
        Professor oliviaLocke = new Professor("Olivia Locke", new BigDecimal(15000.0), StatusProfessor.INATIVO);
        oliviaLocke.setId(3L);

        List<Professor> professores = Arrays.asList(charlotteVanderwall, xavierOsawski, oliviaLocke); //cria lista com os elementos

        ModelAndView mv = new ModelAndView("professores/index");
        mv.addObject("professores", professores);

        return mv;
    }
}
