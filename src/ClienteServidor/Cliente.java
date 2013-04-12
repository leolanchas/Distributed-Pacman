package ClienteServidor;

import java.util.Hashtable;
import javax.naming.*;
import javax.jms.*;

public class Cliente
{
    Context contextoInicial = null;
    QueueSession  sesion = null;
    QueueConnection conexion = null;
    
    public Cliente(String IP, String puerto)
    {
	try 
        {
            //Inicilizamos y obtenemos el contexto
            Hashtable properties = new Hashtable();
            properties.put(Context.INITIAL_CONTEXT_FACTORY, 
                                       "org.exolab.jms.jndi.InitialContextFactory");
            properties.put(Context.PROVIDER_URL, "tcp://" + IP + ":" + puerto + "/" );
            // Conseguimos de la JNDI los objetos administrados
            contextoInicial = new InitialContext(properties);
            QueueConnectionFactory factory =
            (QueueConnectionFactory)contextoInicial.lookup("ConnectionFactory");
            // Creamos la conexion y la sesion
            conexion = factory.createQueueConnection();
            sesion = conexion.createQueueSession(false, sesion.CLIENT_ACKNOWLEDGE);
            // Iniciamos la recepcion de mensajes
            conexion.start();
            
        } 
        catch (NamingException e){ e.printStackTrace(); }
        catch (JMSException e)   { e.printStackTrace(); }
    }
    public void enviarRistra (String mensaje,String destino)
    {
        try 
        {
            System.out.println("Destino Servidor: " + destino );
            Queue cola = (Queue)contextoInicial.lookup(destino);
            // Creamos una sesion de envio
            QueueSender enviaACola = sesion.createSender(cola);
            // Creamos un mensaje
            TextMessage mensajeAEnviar = sesion.createTextMessage();
            mensajeAEnviar.setText(mensaje);
            // Lo enviamos
            System.out.println("Enviando mensaje: " + mensaje);
            enviaACola.send(mensajeAEnviar, DeliveryMode.NON_PERSISTENT, 1, 1000);
        }
        catch (Exception ex) { ex.printStackTrace(); }
       System.out.println("Cliente: Mensaje enviado.");
    }
    
    public String recibirRistra ( String destino, int tEspera )
    {
        String m = null;
        try
        {
            // Creamos una sesion de recepcion
            Queue cola = (Queue)contextoInicial.lookup(destino);
            QueueReceiver recibeDeCola = sesion.createReceiver(cola);
            Message mensaje = recibeDeCola.receive();
            if(mensaje != null)
            {
                //para que borre el mensaje de la cola al recibirlo
                mensaje.acknowledge();
                m = ((TextMessage)mensaje).getText();
            }
        }
        catch (Exception ex) { ex.printStackTrace(); }
        return m;      
    }
    
    public void enviarData (Data objeto, String destino)
    {
        System.out.println("Cliente enviando Data a " + destino);
        try 
        {
            Queue cola = (Queue)contextoInicial.lookup(destino);
            // Creamos una sesion de envio
            QueueSender enviaACola = sesion.createSender(cola);
            // Creamos un objeto            
            ObjectMessage objetoAEnviar = sesion.createObjectMessage(objeto);
            // Lo enviamos
            enviaACola.send(objetoAEnviar,DeliveryMode.NON_PERSISTENT,9,50);
        }
        catch (Exception ex) { ex.printStackTrace(); }
    }
    
    public Data recibirData (String destino,int tEspera)
    {
        try
        {
            // Creamos una sesion de recepcion
            Queue cola = (Queue)contextoInicial.lookup(destino);
            QueueReceiver recibeDeCola = sesion.createReceiver(cola);
            // Iniciamos la recepcion de mensajes
            Message mensaje = recibeDeCola.receive(tEspera);
            if ( mensaje != null )
            {
                //para que borre el mensaje de la cola al recibirlo
                //System.out.println("mensaje " + (((TextMessage)mensaje).getText()));
                mensaje.acknowledge();
                return (Data) ((ObjectMessage)mensaje).getObject();
            }
        }
        catch (Exception ex) { ex.printStackTrace(); }
        return null;      
    }
    
    public void enviarData2 (Data2 objeto,String destino)
    {
        try 
        {
            Queue cola = (Queue)contextoInicial.lookup(destino);
            // Creamos una sesion de envio
            QueueSender enviaACola = sesion.createSender(cola);
            // Creamos un objeto            
            ObjectMessage objetoAEnviar = sesion.createObjectMessage(objeto);
            // Lo enviamos
            enviaACola.send(objetoAEnviar,DeliveryMode.NON_PERSISTENT,9,50);
        }
        catch (Exception ex) { ex.printStackTrace(); }
    }
    
    public Data2 recibirData2 (String destino,int tEspera)
    {
        try
        {
            // Creamos una sesion de recepcion
            Queue cola = (Queue)contextoInicial.lookup(destino);
            QueueReceiver recibeDeCola = sesion.createReceiver(cola);
            // Iniciamos la recepcion de mensajes
            Message mensaje = recibeDeCola.receive(tEspera);
            if(mensaje != null)
            {
                //para que borre el mensaje de la cola al recibirlo
                mensaje.acknowledge();
                Data2 p = (Data2) ((ObjectMessage)mensaje).getObject();
                return p;
            }
        }
        catch (Exception ex) { ex.printStackTrace(); }
        return null;      
    }
    
    public void finalizar()
    {
        try
        {
            sesion.close();
            conexion.close();
            contextoInicial.close();
        }
        catch (Exception ex) { ex.printStackTrace(); } 
    }
}