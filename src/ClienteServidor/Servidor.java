package ClienteServidor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Hashtable;
import javax.naming.*;
import javax.jms.*;

public class Servidor
{
    Context contextoInicial = null;
    QueueSession  sesion = null;
    QueueConnection conexion = null;

    public Servidor(String IP,String puerto)
    {
	try
        {
            //Inicilizamos y obtenemos el contexto
            Hashtable properties = new Hashtable();
            properties.put(Context.INITIAL_CONTEXT_FACTORY,
                                       "org.exolab.jms.jndi.InitialContextFactory");
            properties.put(Context.PROVIDER_URL, "tcp://"+IP+":" + puerto +"/");
            // Conseguimos de la JNDI los objetos administrados
            contextoInicial = new InitialContext(properties);
            QueueConnectionFactory factory =
            (QueueConnectionFactory)contextoInicial.lookup("ConnectionFactory");

            // Creamos la conexion y la sesion
            conexion = factory.createQueueConnection();
            sesion = conexion.createQueueSession(false,sesion.CLIENT_ACKNOWLEDGE);
            conexion.start();

        }
        catch (NamingException e) { e.printStackTrace(); }
        catch (JMSException e)    { e.printStackTrace(); }
    }
    public void enviarRistra (String mensaje,String destino)
    {
        try
        {
            Queue cola = (Queue)contextoInicial.lookup(destino);
            // Creamos una sesion de envio
            QueueSender enviaACola = sesion.createSender(cola);
            // Creamos un mensaje
            TextMessage mensajeAEnviar = sesion.createTextMessage();
            mensajeAEnviar.setText(mensaje);
            // Lo enviamos
            enviaACola.send(mensajeAEnviar,DeliveryMode.NON_PERSISTENT,1,1000);
        }
        catch (Exception ex) { ex.printStackTrace(); }
    }

    public String recibirRistra (String destino,long tEspera)
    {
        String m = null;
        System.out.println("Voy a recibir un mensaje de " + destino);
        try
        {
            // Creamos una sesion de recepcion
            Queue cola = (Queue)contextoInicial.lookup(destino);
            QueueReceiver recibeDeCola = sesion.createReceiver(cola);
            // Iniciamos la recepcion de mensajes
            Message mensaje = recibeDeCola.receiveNoWait();
            if(mensaje != null)
            {
                //para que borre el mensaje de la cola al recibirlo
                mensaje.acknowledge();
                m = ((TextMessage)mensaje).getText();
                System.out.println("Recibido Mensaje" + m);
            }
        }
        catch (Exception ex) { ex.printStackTrace(); }
        return m;
    }

    public void enviarData (Data objeto,String destino)
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
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public Data recibirData (String destino,long tEspera)
    {
        
        try
        {
            // Creamos una sesion de recepcion
            Queue cola = (Queue)contextoInicial.lookup(destino);
            QueueReceiver recibeDeCola = sesion.createReceiver(cola);
            // Iniciamos la recepcion de mensajes
            Message mensaje= recibeDeCola.receive(tEspera);
            if (mensaje != null)
            {
                //para que borre el mensaje de la cola al recibirlo
                mensaje.acknowledge();
                Data p = (Data) ((ObjectMessage)mensaje).getObject();
                return p;
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
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
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public Data2 recibirData2 (String destino,long tEspera)
    {
        
        try
        {
            // Creamos una sesion de recepcion
            Queue cola = (Queue)contextoInicial.lookup(destino);
            QueueReceiver recibeDeCola = sesion.createReceiver(cola);
            // Iniciamos la recepcion de mensajes
            Message mensaje= recibeDeCola.receive(tEspera);
            if (mensaje != null)
            {
                //para que borre el mensaje de la cola al recibirlo
                mensaje.acknowledge();
                Data2 p = (Data2) ((ObjectMessage)mensaje).getObject();
                return p;
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
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
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}