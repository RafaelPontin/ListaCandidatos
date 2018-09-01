package bean;

import java.io.Serializable;

public class Candidatos implements Serializable {

    private Long id;
    private String nome;
    private String partido;
    private String detalhes;
    private String foto;
    private String site;
    private String propostas;
    private Long totalVotos;
    private Long votosPercentuais;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPartido() {
        return partido;
    }

    public void setPartido(String partido) {
        this.partido = partido;
    }

    public String getDetalhes() {
        return detalhes;
    }

    public void setDetalhes(String detalhes) {
        this.detalhes = detalhes;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getPropostas() {
        return propostas;
    }

    public void setPropostas(String propostas) {
        this.propostas = propostas;
    }

    public Long getTotalVotos() {
        return totalVotos;
    }

    public void setTotalVotos(Long totalVotos) {
        this.totalVotos = totalVotos;
    }

    public Long getVotosPercentuais() {
        return votosPercentuais;
    }

    public void setVotosPercentuais(Long votosPercentuais) {
        this.votosPercentuais = votosPercentuais;
    }
}
