from abc import ABC, abstractmethod
import random

# CLASE ABSTRACTA
class Personaje(ABC):
    def __init__(self, nombre):
        self.nombre = nombre
        self.vida = 100
        self.monedas = 0

    def recoger_moneda(self):
        self.monedas += 1
        print(f"{self.nombre} recogió una moneda 💰 Total: {self.monedas}")

    def recibir_dano(self, dano):
        self.vida -= dano
        if self.vida < 0:
            self.vida = 0

    def esta_vivo(self):
        return self.vida > 0

    @abstractmethod
    def atacar(self, enemigo):
        pass


# MARIO
class Mario(Personaje):
    def __init__(self):
        super().__init__("Mario")
        self.tiene_estrella = False

    def usar_poder(self):
        self.tiene_estrella = True
        print("⭐ Mario es invencible!")

    def atacar(self, enemigo):
        if self.tiene_estrella:
            print("Mario destruye al enemigo ⭐")
            enemigo.recibir_dano(100)
        else:
            print("Mario salta sobre el enemigo 🦘")
            enemigo.recibir_dano(30)


# LUIGI
class Luigi(Personaje):
    def __init__(self):
        super().__init__("Luigi")

    def atacar(self, enemigo):
        print("Luigi salta más alto 🟢")
        enemigo.recibir_dano(25)


# ENEMIGO
class Enemigo:
    def __init__(self, tipo):
        self.tipo = tipo
        self.vida = 60

    def recibir_dano(self, dano):
        self.vida -= dano
        if self.vida < 0:
            self.vida = 0

    def esta_vivo(self):
        return self.vida > 0


# JUEGO
jugadores = [Mario(), Luigi()]
enemigo = Enemigo("Goomba")

print("🍄 INICIA EL JUEGO DE MARIO 🍄")

for p in jugadores:
    if not enemigo.esta_vivo():
        break

    if random.choice([True, False]):
        p.recoger_moneda()

    if isinstance(p, Mario) and random.choice([True, False]):
        p.usar_poder()

    p.atacar(enemigo)
    print("Vida del enemigo:", enemigo.vida)
    print("-------------------")

print("🏁 Fin del juego")
