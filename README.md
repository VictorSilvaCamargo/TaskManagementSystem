Gerenciador de tarefas
Configurações:
-> Spring web
-> Spring JPA
-> Spring DevTools
-> MySQL connector
  -> Nome do banco de dados: taskmanager_db
  -> Username : root
  -> senha : admin
-> JUnit
-> Mokito

![Captura de tela 2024-06-23 222942](https://github.com/VictorSilvaCamargo/TaskManagementSystem/assets/107776635/8355fc20-bb93-4294-a0ca-f2eba983fed4)


![Captura de tela 2024-06-23 222954](https://github.com/VictorSilvaCamargo/TaskManagementSystem/assets/107776635/f18161aa-9372-4c6d-a202-c7d49a8cda98)

![Captura de tela 2024-06-23 223002](https://github.com/VictorSilvaCamargo/TaskManagementSystem/assets/107776635/f0a2230a-daba-4873-b4af-6b4127fa2f8c)


O gerenciador cria tarefas apenas em dias de semana, e so podem ser deletadas depois de 5 dias com localDate, também so podem receber alguma atualização se o status da tarefa for pendente.

As tarefas ciradas ficam com detalhes em azul e possuem as opções de deletar e completar:

![Captura de tela 2024-06-23 223044](https://github.com/VictorSilvaCamargo/TaskManagementSystem/assets/107776635/0563d685-99c7-436d-8744-1384b8e2c280)

![Captura de tela 2024-06-23 223052](https://github.com/VictorSilvaCamargo/TaskManagementSystem/assets/107776635/756f1bb3-7adf-4488-9bee-26827ea24142)

Se marcar como completada ela ficará em verde:

![Captura de tela 2024-06-23 223101](https://github.com/VictorSilvaCamargo/TaskManagementSystem/assets/107776635/85ca6b8d-92b1-483a-b4bb-88a1610194e8)

E o banco de dados sera atualizado:

![Captura de tela 2024-06-23 223111](https://github.com/VictorSilvaCamargo/TaskManagementSystem/assets/107776635/136e525e-1295-4e9c-acbc-ce3684e128c9)

Outras capturas de tela que demonstram a funcionalidade:
![Captura de tela 2024-06-23 223127](https://github.com/VictorSilvaCamargo/TaskManagementSystem/assets/107776635/04d6dfd4-91a0-4753-8b71-45dac1159f15)
![Captura de tela 2024-06-23 223155](https://github.com/VictorSilvaCamargo/TaskManagementSystem/assets/107776635/ffd489b3-4de5-4a93-9b4b-9d0e99517167)

Não estava nos requisitos, mas decidi usar testes para o projeto:
![Captura de tela 2024-06-23 222657](https://github.com/VictorSilvaCamargo/TaskManagementSystem/assets/107776635/a8a9d752-0258-4be8-9690-a83177125961)
![Captura de tela 2024-06-23 222648](https://github.com/VictorSilvaCamargo/TaskManagementSystem/assets/107776635/4588cf2b-7914-45a1-a2b6-ea147127bfb2)
![Captura de tela 2024-06-23 220804](https://github.com/VictorSilvaCamargo/TaskManagementSystem/assets/107776635/fae05357-75ff-4d16-813a-847836408b81)
