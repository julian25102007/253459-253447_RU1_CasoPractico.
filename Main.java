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
}

class EncargadoRH {
    Departamento[] departamentos;

    EncargadoRH(Departamento[] departamentos) {
        this.departamentos = departamentos;
    }

    Departamento buscarDepartamento(int opcion) {
        return departamentos[opcion - 1];
    }

    Empleado consultarEmpleado(Departamento d, int id) {
        return d.buscarEmpleado(id);
    }
}

public class Main {
    public static void main(String[] args) {
        Departamento[] departamentos = new Departamento[5];
        departamentos[0] = new Departamento("Ventas", "Oficina central");
        departamentos[1] = new Departamento("Finanzas", "Sucursal");
        departamentos[2] = new Departamento("Sistemas", "Oficina central");
        departamentos[3] = new Departamento("Compras", "Sucursal");
        departamentos[4] = new Departamento("Logistica", "Sucursal");

        departamentos[0].agregarEmpleado(new Empleado("Ana Lopez", 10001));
        departamentos[0].agregarEmpleado(new Empleado("Luis Perez", 10002));
        departamentos[2].agregarEmpleado(new Empleado("Carlos Ruiz", 30001));

        EncargadoRH rh = new EncargadoRH(departamentos);
        Scanner sc = new Scanner(System.in);

        System.out.println("Departamentos:");
        for (int i = 0; i < departamentos.length; i++) {
            System.out.println((i + 1) + ". " + departamentos[i].nombre);
        }

        System.out.print("Seleccione un departamento (1-5): ");
        int opcion = sc.nextInt();
        Departamento d = rh.buscarDepartamento(opcion);

        System.out.print("Identificador del empleado (5 digitos): ");
        int id = sc.nextInt();
        Empleado e = rh.consultarEmpleado(d, id);

        if (e == null) {
            System.out.println("Empleado no encontrado.");
        } else {
            System.out.println("Nombre: " + e.nombreCompleto);
            System.out.println("Identificador: " + e.identificador);
            System.out.println("Departamento: " + d.nombre);
            System.out.println("Ubicacion: " + d.ubicacion);
        }

        sc.close();
    }
}