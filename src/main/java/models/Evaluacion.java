/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author Wilber's-Laptop
 */
public class Evaluacion {
    private int id;
    private String fecha;
    private double calificacion;
    private Estudiante estudiante;
    private Curso curso;

    public Evaluacion() {
    }

    public Evaluacion(String fecha, double calificacion, Estudiante estudiante, Curso curso) {
        this.fecha = fecha;
        this.calificacion = calificacion;
        this.estudiante = estudiante;
        this.curso = curso;
    }

    public Evaluacion(int id, String fecha, double calificacion, Estudiante estudiante, Curso curso) {
        this.id = id;
        this.fecha = fecha;
        this.calificacion = calificacion;
        this.estudiante = estudiante;
        this.curso = curso;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public double getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(double calificacion) {
        this.calificacion = calificacion;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }
}
