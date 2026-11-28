import java.util.Scanner;

class Empleado {
    String nombreCompleto;
    int identificador;

    Empleado(String nombreCompleto, int identificador) {
        this.nombreCompleto = nombreCompleto;
        this.identificador = identificador;
    }
}

class Departamento {
    String nombre;
    String ubicacion;
    Empleado[] empleados = new Empleado[10];
    int numEmpleados = 0;

    Departamento(String nombre, String ubicacion) {
        this.nombre = nombre;
        this.ubicacion = ubicacion;
    }

    void agregarEmpleado(Empleado e) {
        if (numEmpleados < empleados.length) {
            empleados[numEmpleados] = e;
            numEmpleados++;
        }
    }

    Empleado buscarEmpleado(int id) {
        for (int i = 0; i < numEmpleados; i++) {
            if (empleados[i].identificador == id) {
                return empleados[i];
            }
        }
        return null;
    }

    void mostrarEmpleados() {
        if (numEmpleados == 0) {
            System.out.println("Este departamento no tiene empleados.");
            return;
        }
        for (int i = 0; i < numEmpleados; i++) {
            System.out.println(empleados[i].identificador + " - " + empleados[i].nombreCompleto);
        }
    }
}

class EncargadoRH {
    Departamento[] departamentos;

    EncargadoRH(Departamento[] departamentos) {
        this.departamentos = departamentos;
    }

    Departamento buscarDepartamento(int opcion) {
        if (opcion < 1 || opcion > departamentos.length) {
            return null;
        }
        return departamentos[opcion - 1];
    }

    Empleado consultarEmpleado(Departamento d, int id) {
        return d.buscarEmpleado(id);
    }
}

public class Main {
    static final int NUM_DEPARTAMENTOS = 5;

    static int leerEntero(Scanner sc) {
        while (!sc.hasNextInt()) {
            sc.next();
            System.out.print("Escriba un numero valido: ");
        }
        return sc.nextInt();
    }

    static Departamento[] crearDepartamentos() {
        Departamento[] departamentos = new Departamento[NUM_DEPARTAMENTOS];
        departamentos[0] = new Departamento("Ventas", "Oficina central");
        departamentos[1] = new Departamento("Finanzas", "Sucursal");
        departamentos[2] = new Departamento("Sistemas", "Oficina central");
        departamentos[3] = new Departamento("Compras", "Sucursal");
        departamentos[4] = new Departamento("Logistica", "Sucursal");
        return departamentos;
    }

    static void cargarEmpleadosDePrueba(Departamento[] departamentos) {
        departamentos[0].agregarEmpleado(new Empleado("Ana Lopez", 10001));
        departamentos[0].agregarEmpleado(new Empleado("Luis Perez", 10002));
        departamentos[2].agregarEmpleado(new Empleado("Carlos Ruiz", 30001));
    }

    static void mostrarDepartamentos(Departamento[] departamentos) {
        System.out.println("Departamentos:");
        for (int i = 0; i < departamentos.length; i++) {
            System.out.println((i + 1) + ". " + departamentos[i].nombre + " (" + departamentos[i].ubicacion + ")");
        }
    }

    static void consultarPorIdentificador(EncargadoRH rh, Departamento d, Scanner sc) {
        System.out.print("Identificador del empleado (5 digitos): ");
        int id = leerEntero(sc);
        Empleado e = rh.consultarEmpleado(d, id);

        if (e == null) {
            System.out.println("Empleado no encontrado.");
            return;
        }
        System.out.println("Nombre: " + e.nombreCompleto);
        System.out.println("Identificador: " + e.identificador);
        System.out.println("Departamento: " + d.nombre);
        System.out.println("Ubicacion: " + d.ubicacion);
    }

    static void atenderDepartamento(EncargadoRH rh, Departamento d, Scanner sc) {
        System.out.println("1. Consultar empleado por identificador");
        System.out.println("2. Ver todos los empleados del departamento");
        System.out.print("Seleccione una opcion (1-2): ");
        int accion = leerEntero(sc);

        if (accion == 1) {
            consultarPorIdentificador(rh, d, sc);
        } else if (accion == 2) {
            System.out.println("Empleados de " + d.nombre + ":");
            d.mostrarEmpleados();
        } else {
            System.out.println("Opcion no valida.");
        }
    }

    static void ejecutarMenu(EncargadoRH rh, Departamento[] departamentos, Scanner sc) {
        String seguir = "s";

        while (seguir.equalsIgnoreCase("s")) {
            mostrarDepartamentos(departamentos);
            System.out.print("Seleccione un departamento (1-" + NUM_DEPARTAMENTOS + "): ");
            Departamento d = rh.buscarDepartamento(leerEntero(sc));

            if (d == null) {
                System.out.println("Departamento no valido.");
            } else {
                atenderDepartamento(rh, d, sc);
            }

            System.out.print("¿Hacer otra consulta? (s/n): ");
            seguir = sc.next();
        }
    }

    public static void main(String[] args) {
        Departamento[] departamentos = crearDepartamentos();
        cargarEmpleadosDePrueba(departamentos);

        EncargadoRH rh = new EncargadoRH(departamentos);
        Scanner sc = new Scanner(System.in);

        ejecutarMenu(rh, departamentos, sc);

        sc.close();
    }
}