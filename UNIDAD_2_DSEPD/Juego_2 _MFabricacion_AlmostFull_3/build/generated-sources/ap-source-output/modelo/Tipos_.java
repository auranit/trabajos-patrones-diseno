package modelo;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.Palabras;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-11-30T08:14:32")
@StaticMetamodel(Tipos.class)
public class Tipos_ { 

    public static volatile SingularAttribute<Tipos, Integer> idTipos;
    public static volatile SingularAttribute<Tipos, String> descripcion;
    public static volatile ListAttribute<Tipos, Palabras> palabrasList;

}