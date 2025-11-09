🌦️ WeatherApp

Aplicativo Android desenvolvido como parte de um projeto acadêmico para a disciplina de Programação Mobile.
O app exibe uma tela de abertura (Splash) com o nome do aplicativo e o logotipo, uma tela principal (Main) e uma tela de informações (Sobre) com botão de retorno.

🧩 Estrutura do Projeto

O projeto é composto por três telas principais:

🌀 SplashActivity

Tela de abertura do aplicativo

Mostra o logotipo e o nome do app

Faz a transição automática para a tela principal após 3 segundos

🏠 MainActivity

Tela principal do app

Exibe o título “WeatherApp”

Contém um botão “Sobre” que leva à próxima tela

ℹ️ SobreActivity

Tela com informações sobre o aplicativo

Explica o propósito e o funcionamento do app

Possui um botão “Voltar” que retorna à tela principal

🎨 Layouts (XML)

Os layouts do app estão localizados em:
📂 app/src/main/res/layout/

Tela	Arquivo	Descrição
Splash	activity_splash.xml	Tela de introdução com o logotipo e nome do app
Principal	activity_main.xml	Tela inicial com o título e o botão “Sobre”
Sobre	activity_sobre.xml	Tela informativa com botão de retorno
⚙️ Tecnologias Utilizadas

Linguagem: Java ☕

IDE: Android Studio 🧰

Layouts: XML

SDK: Android API 34 (Android 14)

Arquitetura: Activities + Intents

Compatibilidade: Android 8.0 (Oreo) ou superior

🚀 Funcionalidades

✅ Tela de abertura com animação e transição automática
✅ Navegação entre telas com Intent
✅ Layouts responsivos e limpos
✅ Paleta de cores azul (#2196F3, #E3F2FD)
✅ Ícones padrão do Android (customizáveis)

🛠️ Como Executar o Projeto

Abra o projeto no Android Studio

Clique em Build → Rebuild Project

Após “BUILD SUCCESSFUL”, clique em Run ▶️

Selecione um emulador (ex: Pixel 2 API 30)

O app será executado e iniciará com a tela de abertura (Splash)

📦 Geração do APK

Para gerar o APK de instalação:

Vá até Build → Generate Signed Bundle / APK...

Escolha APK → Next → Create Key Store (se necessário)

Clique em Finish

O arquivo será gerado em:

app/build/outputs/apk/debug/app-debug.apk

👨‍💻 Autor

Matheus Lima
📧 [adicione seu e-mail se quiser]
💡 Desenvolvido com fins educacionais – Projeto acadêmico Android Studio.

📝 Licença

Este projeto é de uso acadêmico e pode ser adaptado livremente para fins de estudo e aprendizado.