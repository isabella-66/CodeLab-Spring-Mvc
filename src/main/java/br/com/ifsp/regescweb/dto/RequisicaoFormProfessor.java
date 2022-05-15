package br.com.ifsp.regescweb.dto;

import br.com.ifsp.regescweb.models.Professor;
import br.com.ifsp.regescweb.models.StatusProfessor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

//Classe DTO (Data Transfer Object) - impede inserções indesejadas/maliciosas
public class RequisicaoFormProfessor {
    @NotNull
    @NotBlank
    private String nome; //em caso de erro: NotBlank.requisicaoNovoProfessor.nome
    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal salario;
    private StatusProfessor statusProfessor;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    public StatusProfessor getStatusProfessor() {
        return statusProfessor;
    }

    public void setStatusProfessor(StatusProfessor statusProfessor) {
        this.statusProfessor = statusProfessor;
    }

    public Professor toProfessor() {
        Professor professor = new Professor();
        professor.setNome(this.nome);
        professor.setSalario(this.salario);
        professor.setStatusProfessor(this.statusProfessor);

        return professor;
    }//conversão parâmetro para objeto

    public Professor toProfessor (Professor professor) {
        professor.setNome(this.nome); //sem get porque está no próprio objeto da requisição
        professor.setSalario(this.salario);
        professor.setStatusProfessor(this.statusProfessor);
        return professor;
    } //atualização dos objetos de requisição - para editar

    public void fromProfessor(Professor professor) { //recuperando informações dos dados do banco
        this.nome = professor.getNome();
        this.salario = professor.getSalario();
        this.statusProfessor = professor.getStatusProfessor();
    }

    @Override
    public String toString() {
        return "RequisicaoNovoProfessor{" +
                "nome= " + nome +
                ", salário= " + salario +
                ", statusProfessor= " + statusProfessor +
                '}';
    }
}
