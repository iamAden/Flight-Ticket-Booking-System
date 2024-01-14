const signUpButton = document.querySelector("#signUp");
const signInButton = document.querySelector("#signIn");
const container = document.querySelector(".container");
const signUpButton2 = document.querySelector("#signUp2");
const signInButton2 = document.querySelector("#signIn2");

signUpButton.addEventListener('click', () =>{
    container.classList.add("sign-up-mode");
    });

signInButton.addEventListener('click', () =>{
    container.classList.remove("sign-up-mode");
    });

    signUpButton2.addEventListener('click', () =>{
        container.classList.add("sign-up-mode2");
        });

    signInButton2.addEventListener('click', () =>{
        container.classList.remove("sign-up-mode2");
    });

    