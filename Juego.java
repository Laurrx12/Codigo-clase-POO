import java.util.ArrayList;
import java.util.Random;

// CLASE PRINCIPAL (PRIMERO)
public class MarioGame {
    public static void main(String[] args) {

        ArrayList<Personaje> jugadores = new ArrayList<>();
        jugadores.add(new Mario());
        jugadores.add(new Luigi());

        Enemigo goomba = new Enemigo("Goomba");
        Random rand = new Random();

        System.out.println("🍄 INICIA EL JUEGO DE MARIO 🍄");

        int turno = 1;

        while (goomba.estaVivo()) {
            System.out.println("\n--- TURNO " + turno + " ---");

            for (Personaje p : jugadores) {

                if (!p.estaVivo()) continue;
                if (!goomba.estaVivo()) break;

                if (rand.nextBoolean()) {
                    p.recogerMoneda();
                }

                if (p instanceof PoderEspecial && rand.nextBoolean()) {
                    ((PoderEspecial) p).usarPoder();
                }

                p.atacar(goomba);

                System.out.println("Vida del " + goomba.getTipo() + ": " + goomba.getVida());

                if (goomba.estaVivo()) {
                    goomba.atacar(p);
                    System.out.println(p.getNombre() + " vida: " + p.getVida());
                }

                System.out.println("-------------------");
            }

            turno++;
        }

        System.out.println("🏁 Fin del juego");
    }
}

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

    public String getNombre() {
        return nombre;
    }

    public int getVida() {
        return vida;
    }

    public abstract void atacar(Enemigo e);
}

// MARIO
class Mario extends Personaje implements PoderEspecial {
    private int turnosEstrella = 0;

    public Mario() {
        super("Mario");
    }

    public void activarEstrella() {
        turnosEstrella = 2;
        System.out.println("⭐ Mario es invencible por 2 turnos!");
    }

    @Override
    public void usarPoder() {
        activarEstrella();
    }

    @Override
    public void atacar(Enemigo e) {
        if (turnosEstrella > 0) {
            System.out.println("⭐ Mario destruye al enemigo!");
            e.recibirDano(50);
            turnosEstrella--;
        } else {
            System.out.println("Mario salta sobre el enemigo 🦘");
            e.recibirDano(20);
        }
    }
}

// LUIGI
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
        this.vida = 80;
    }

    public void recibirDano(int dano) {
        vida -= dano;
        if (vida < 0) vida = 0;
    }

    public void atacar(Personaje p) {
        System.out.println(tipo + " ataca a " + p.getNombre() + " 👾");
        p.recibirDano(15);
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
