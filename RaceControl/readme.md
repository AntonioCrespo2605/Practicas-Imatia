Ejercicio Race Control: 

Se desea crear un programa para controlar todo el ciclo de la celebración de eventos automovilísticos.

* El Control central del programa tiene que gestionar la realización de Torneos y de Carreras, además de los diferentes Garajes que participan en ambas competiciones automovilísticas.

* Los Coches tienen una Marca y un Modelo que los identifica y se registran en los Garajes. El Garaje al que pertenece un coche, le pone una pegatina con el nombre del garaje para identificarlo. Todos los coches tienen la misma velocidad máxima, de tal manera que la velocidad que adquiera durante una vuelta está marcada por la pericia del piloto, que parece que acelera y frena el coche aleatoriamente una vez por minuto. Se aplicará un valor de aceleración o deceleración instantáneo, por ejemplo, en intervalos de 10 Km/h.

* Las Carreras tienen un nombre para poder identificarlas. Dichas Carreras son de dos tipos: Estándar (que puede durar cualquier número de horas, pero que normalmente son 3 horas) o carreras de Eliminación (que tienen una serie de minutos previos para que los pilotos se hagan a la pista, y al terminar esos minutos de calentamiento, se irá retirando el coche que va en la última posición, cada minuto, hasta que sólo quede un coche).

* Aunque, las carreras sean diferentes, todas ellas tienen varias características en común; como puede ser el registros de los garajes que participan en la carrera, que coches de cada garaje compiten, el podio de cada carrera, etc.

* Los Torneos son agrupaciones de Carreras. Los Torneos tienen un nombre que los identifica y, normalmente, están formados por 10 carreras, aunque este número puede variar. En los Torneos, se registran los Garajes que van a participar en cada una de las carreras.

* Todas las Carreras y Torneos se disputan en circuitos de fórmula NASCAR que, dada su estructura, hace que los coches no necesiten girar.

* En los Torneos y las Carreras, pueden participar uno o varios Garajes. Si participa uno, todos sus coches entrarán en la competición. En el caso de ser más de uno, solo competirá un coche de cada Garaje, elegido de forma aleatoria.

* El resultado de cualquier carrera dependerá de la distancia recorrida en el mismo tiempo por cada coche que participa en la carrera.

* El podio de una carrera son los 3 Coches que más distancia hayan recorrido en el mismo tiempo.

* Un Torneo tendrá puntuaciones para cada coche que participe. Se entregarán puntuaciones a los coches del podio de cada carrera, de mayor a menor, de manera proporcional. Estas puntuaciones son acumulativas para cada coche.

* El ganador del torneo será el coche que más puntos tenga al finalizar el torneo. En caso de empate, se divide el premio.

* En la aplicación, se pretende guardar el estado del programa, de manera que no sea necesario insertar de nuevo la información de los Coches, Garajes, Carreras y Torneos en un fichero.

* Este fichero debe cargarse cada vez que se inicia el programa y debe actualizarse antes de finalizar el programa.