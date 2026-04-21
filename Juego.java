import java.util.ArrayList;
import java.util.Random;

// INTERFACE
interface PoderEspecial {
    void usarPoder();
}

// CLASE ABSTRACTA
abstract class Personaje {
    protected String nombre;
    protected int vida;
    protected int monedas;

    public Personaje(String nombre) {
        this.nombre = nombre;
        this.vida = 100;
        this.monedas = 0;
    }

    public void recogerMoneda() {
        monedas++;
        System.out.println(nombre + " recogió una moneda 💰 Total: " + monedas);
    }

    public void recibirDano(int dano) {
        vida -= dano;
        if (vida < 0) vida = 0;
    }

    public boolean estaVivo() {
        return vida > 0;
    }

    public abstract void atacar(Enemigo e);
}

// CLASE MARIO
class Mario extends Personaje implements PoderEspecial {
    private boolean tieneEstrella = false;

    public Mario() {
        super("Mario");
    }

    public void activarEstrella() {
        tieneEstrella = true;
        System.out.println("⭐ Mario es invencible!");
    }

    @Override
    public void usarPoder() {
        activarEstrella();
    }

    @Override
    public void atacar(Enemigo e) {
        if (tieneEstrella) {
            System.out.println("Mario destruye al enemigo con estrella ⭐");
            e.recibirDano(100);
        } else {
            System.out.println("Mario salta sobre el enemigo 🦘");
            e.recibirDano(30);
        }
    }
}

// CLASE LUIGI
class Luigi extends Personaje {
    public Luigi() {
        super("Luigi");
    }

    @Override
    public void atacar(Enemigo e) {
        System.out.println("Luigi salta más alto 🟢");
        e.recibirDano(25);
    }
}

// ENEMIGO
class Enemigo {
    private String tipo;
    private int vida;

    public Enemigo(String tipo) {
        this.tipo = tipo;
        this.vida = 60;
    }

    public void recibirDano(int dano) {
        vida -= dano;
        if (vida < 0) vida = 0;
    }

    public boolean estaVivo() {
        return vida > 0;
    }

    public String getTipo() {
        return tipo;
    }

    public int getVida() {
        return vida;
    }
}

// JUEGO PRINCIPAL
public class MarioGame {
    public static void main(String[] args) {

        ArrayList<Personaje> jugadores = new ArrayList<>();
        jugadores.add(new Mario());
        jugadores.add(new Luigi());

        Enemigo goomba = new Enemigo("Goomba");

        Random rand = new Random();

        System.out.println("🍄 INICIA EL JUEGO DE MARIO 🍄");

        for (Personaje p : jugadores) {

            if (!goomba.estaVivo()) break;

            // Probabilidad de moneda
            if (rand.nextBoolean()) {
                p.recogerMoneda();
            }

            // Probabilidad de poder especial
            if (p instanceof PoderEspecial && rand.nextBoolean()) {
                ((PoderEspecial) p).usarPoder();
            }

            p.atacar(goomba);

            System.out.println("Vida del " + goomba.getTipo() + ": " + goomba.getVida());
            System.out.println("-------------------");
        }

        System.out.println("🏁 Fin del juego");
    }
}
