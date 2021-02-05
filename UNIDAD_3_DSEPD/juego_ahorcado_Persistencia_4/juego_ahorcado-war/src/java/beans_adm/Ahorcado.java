/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans_adm;

import accesodatos.TiposFacade;
import javax.faces.event.ActionEvent;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.ApplicationScoped;
import log_neg.JuegosUsuario_ln;
import log_neg.Tipo_p;
import log_neg.Usuarios_ln;
import log_neg.palabras_ln;
import modelo.JuegosUsuario;
import modelo.Palabras;
import modelo.Tipos;
import modelo.Usuario;

/**
 *
 * @author lithium
 */
@Named(value = "ahorcado")
@ApplicationScoped
public class Ahorcado implements Serializable{

    @EJB
    private JuegosUsuario_ln juegosUsuario_ln;
    
    @EJB
    private Usuarios_ln usuarios_ln;
    
    @EJB
    private Tipo_p tipo_p;
    
    @EJB
    private palabras_ln palabras_ln;
    
    
    private Palabras palabras; 
    private Tipos tipos;
    private Usuario usuarios;
    private JuegosUsuario juegosusuario;
    private ArrayList<Integer> Niveles;// = new ArrayList();
    private ArrayList<String> tipos_persona;// = new ArrayList();
    
    private ArrayList<Character> letras;
    private ArrayList<Character> letras_aux;
    private ArrayList<String> posiciones;
    private ArrayList<Character> letrasabecedario;
    private ArrayList<Character> letras_descubiertas;
    private String palabra = "ABRACADABRA";    
    private String ida;
    private String abecedario = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private String guiones = "___________";
    private String nombre_usuario;
    private int intentos;
    private boolean hayintentos;
    private String mensaje;

    public Palabras getPalabras() {
        return palabras;
    }

    public void setPalabras(Palabras palabras) {
        this.palabras = palabras;
    }
    public List<Palabras> lista_p(){
        return palabras_ln.listaPalabras();
    }

    public Tipos getTipos() {
        return tipos;
    }

    public void setTipos(Tipos tipos) {
        this.tipos = tipos;
    }
    public List<Tipos> lista_t(){
        return tipo_p.lista_t();
    } 

    public Usuario getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Usuario usuarios) {
        this.usuarios = usuarios;
    }
    public List<Usuario> lista_u(){
        return usuarios_ln.listaUsuarios();
    }

    public JuegosUsuario getJuegosusuario() {
        return juegosusuario;
    }

    public void setJuegosusuario(JuegosUsuario juegosusuario) {
        this.juegosusuario = juegosusuario;
    }
    
    public List<JuegosUsuario> lista_ju(){
        return juegosUsuario_ln.listaJuegosUsuario();
    }
    
    /*
    public ArrayList<Integer> get_niveles(){      
        for (int i = 0; i < palabras_ln.listaPalabras().size(); i++) {
            if (!Niveles.contains(palabras_ln.listaPalabras().get(i).getNivel())) {
                Niveles.add(palabras_ln.listaPalabras().get(i).getNivel());
            }
        }
        return Niveles;
    }
    */
    
    public void guarda_p(ActionEvent e){
        palabras.setTipo(tipos);
        palabras_ln.guardar(palabras);
        palabras = new Palabras();
    }
    public void registrar_usuario(ActionEvent e){
        usuarios.setFechaRegistro(obtenerFecha());
        usuarios_ln.registrar(usuarios);
        usuarios = new Usuario();
    }
    
    /**
     * Creates a new instance of Ahorcado
     */
    public Ahorcado() {
        Niveles = new ArrayList();
        tipos_persona = new ArrayList();
        
        palabras = new Palabras();
        tipos = new Tipos();
        usuarios = new Usuario();
        
        mensaje="";
        hayintentos = false;
        nombre_usuario = "";
        intentos = 0;
        posiciones = new ArrayList();
        
        ida = new String("");
        letras = new ArrayList();         
        for (int i = 0; i < palabra.length(); i++) {
            letras.add(palabra.charAt(i));
        } 
        
        letras_aux = new ArrayList(); 
        for (int i = 0; i < palabra.length(); i++) {
            letras_aux.add(palabra.charAt(i));
        }
        letrasabecedario = new ArrayList();
        for (int i = 0; i < abecedario.length(); i++) {
            letrasabecedario.add(abecedario.charAt(i));
        }
        
        letras_descubiertas = new ArrayList();
        for (int i = 0; i < guiones.length(); i++) {
            letras_descubiertas.add(guiones.charAt(i));
        }
    }
    
    public ArrayList<String> get_tipos_persona(){
        tipos_persona.add("n");
        tipos_persona.add("a");
        tipos_persona.add("j");
        tipos_persona.add("d");
        tipos_persona.add("m");
        
        return tipos_persona;
    }
    
    public String descripcion_tpersona(String tipo_persona){
        String descripcion = "";
        if (tipo_persona.equals("n")) {
            descripcion = "Niño";
        }
        if (tipo_persona.equals("a")) {
            descripcion = "Adolescente";
        }
        if (tipo_persona.equals("j")) {
            descripcion = "Joven";
        }
        if (tipo_persona.equals("d")) {
            descripcion = "Adulto";
        }
        if (tipo_persona.equals("m")) {
            descripcion = "Adulto Mayor";
        }
        return descripcion;
    }
    
    public ArrayList<Integer> get_niveles(){      
        for (int i = 0; i < 4; i++) {
             Niveles.add(i+1);
        }
        return Niveles;
    }
    
    public String nombre_nivel(Integer Nivel){
        String nombre_nivel = ""; 
            if (Nivel == 1) {
                nombre_nivel = "Facil";
            }
            if (Nivel == 2) {
                nombre_nivel = "Medio";
            }
            
            if (Nivel == 3) {
                nombre_nivel = "Dificil";
            }
            if (Nivel == 4) {
                nombre_nivel = "Muy Dificil";
            }
        return nombre_nivel;
    }
    
    public String buscar_usuario(){
        boolean usuarioExiste = false;
        for (int i = 0; i < usuarios_ln.listaUsuarios().size(); i++) {
            if (usuarios_ln.listaUsuarios().get(i).getNombre().equals(nombre_usuario)) { //si el usuario ya esta registrado
                usuarioExiste = true;
                break;
            }
        }
        if (usuarioExiste == true) {
            return "juego_1.xhtml"; 
        }else{
            return "agregar_usuario.xhtml";
        }
    }
    
    public Date obtenerFecha(){
        Calendar calendario = Calendar.getInstance();
        DateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        String fechaR="";
        Date fechaRegistro = null; 
 
        fechaR = formatoFecha.format(calendario.getTime());
        
        try {
            fechaRegistro = formatoFecha.parse(fechaR);
        } catch (ParseException ex) {
            Logger.getLogger(Ahorcado.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fechaRegistro;
    }
    
    public void visualizar(ActionEvent e){
        ida = e.getComponent().getId();  //"getComponent()" permite saber el componente que realizo el evento. Y de ese componente se obtiene el ID,que es el nombre que se llego a formar por 2 valores: el valor de la letra a la que se le dió click y la posición de la letra
        posiciones.add(ida);       
    }
    
    public void actualizar(ActionEvent e){
        ida = e.getComponent().getId();  //"getComponent()" permite saber el componente que realizo el evento. Y de ese componente se obtiene el ID,que es el nombre que se llego a formar por 2 valores: el valor de la letra a la que se le dió click y la posición de la letra
        Character ka = ida.charAt(0); //del ID, solo se obtiene la letra 
        int pos = -1;
        String posicion = "";
        //posiciones.add(ida);
        
        for (int i = 0; i < palabra.length(); i++) {
            if ((pos = letras.indexOf(ka))!= -1) {// se compara si la posicion de la letra es diferente de -1 y si es así, eso significa que se encontró la letra
            //"pos = letras.indexOf(ka)" regresa la posición de la letra a la que se le dió click
                letras.set(pos, '_');
                posicion = ka.toString()+pos;
                posiciones.add(posicion);
                //letras_descubiertas.set(pos,ka);
            }
        }
        
    }
    
    public void actualizar_letras(ActionEvent e){
        if (intentos > 0) {
                ida = e.getComponent().getId();  //"getComponent()" permite saber el componente que realizo el evento. Y de ese componente se obtiene el ID,que es el nombre que se llego a formar por 2 valores: el valor de la letra a la que se le dió click y la posición de la letra
                Character ka = ida.charAt(0); //del ID, solo se obtiene la letra 
                //int pos = -1;
               // for (int i = 0; i < palabra.length(); i++) {
                    if ((palabra.indexOf(ka))!= -1) {// se compara si la posicion de la letra es diferente de -1 y si es así, eso significa que se encontró la letra                
                        for (int j = 0; j < letras_aux.size(); j++) {
                            if (letras_aux.get(j).equals(ka)) {
                                 letras_descubiertas.set(j,ka);
                            }
                        }
                        letrasabecedario.remove(ka);
                    }else{
                        intentos--;
                    }
              //}
              
                    if (intentos == 0) {
                        hayintentos = true;
                        mensaje = "YA NO QUEDAN MÁS INTENTOS";
                    }
                    if (!letras_descubiertas.contains('_')) {
                         hayintentos = true;
                         mensaje = "¡JUEGO TERMINADO!";
                    }    
        }
    }
   
    public void reiniciar(){
        mensaje="";
        hayintentos = false;
        nombre_usuario = "";
        intentos = 0;
        posiciones = new ArrayList();
        
        ida = new String("");
        letras = new ArrayList();         
        for (int i = 0; i < palabra.length(); i++) {
            letras.add(palabra.charAt(i));
        } 
        
        letras_aux = new ArrayList(); 
        for (int i = 0; i < palabra.length(); i++) {
            letras_aux.add(palabra.charAt(i));
        }
        letrasabecedario = new ArrayList();
        for (int i = 0; i < abecedario.length(); i++) {
            letrasabecedario.add(abecedario.charAt(i));
        }
        
        letras_descubiertas = new ArrayList();
        for (int i = 0; i < guiones.length(); i++) {
            letras_descubiertas.add(guiones.charAt(i));
        }
    }

    
    public String getIda() {
        return ida;
    }

    public void setIda(String ida) {
        this.ida = ida;
    }

    public ArrayList<Character> getLetras() {
        return letras;
    }

    public void setLetras(ArrayList<Character> letras) {
        this.letras = letras;
    }    
    //EJERCICIO A HACER: MOSTRAR EL VALOR DE "metadato.index" en "juego.xhtml"
    
    //EJERCICIO 2: PONER EL NOMBRE DEL PARTICIPANTE, EL NUMERO DE INTENTOS TOTALES, LOS INTENTOS REALIZADOS Y LOS INTENTOS RESTANTES

    public ArrayList<String> getPosiciones() {
        return posiciones;
    }

    public void setPosiciones(ArrayList<String> posiciones) {
        this.posiciones = posiciones;
    }

    public ArrayList<Character> getLetrasabecedario() {
        return letrasabecedario;
    }

    public void setLetrasabecedario(ArrayList<Character> letrasabecedario) {
        this.letrasabecedario = letrasabecedario;
    }

    public ArrayList<Character> getLetras_descubiertas() {
        return letras_descubiertas;
    }

    public void setLetras_descubiertas(ArrayList<Character> letras_descubiertas) {
        this.letras_descubiertas = letras_descubiertas;
    }

    public ArrayList<Character> getLetras_aux() {
        return letras_aux;
    }

    public void setLetras_aux(ArrayList<Character> letras_aux) {
        this.letras_aux = letras_aux;
    }

    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }
    
    public int getIntentos() {
        return intentos;
    }

    public void setIntentos(int intentos) {
        this.intentos = intentos;
    }

    public boolean isHayintentos() {
        return hayintentos;
    }

    public void setHayintentos(boolean hayintentos) {
        this.hayintentos = hayintentos;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
}