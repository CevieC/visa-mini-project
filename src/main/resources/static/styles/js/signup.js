const signUpButton = document.getElementById("signUp");
const signInButton = document.getElementById("signIn");
const container = document.getElementById("container");

signUpButton.addEventListener("click", () => {
  container.classList.add("right-panel-active");
});

signInButton.addEventListener("click", () => {
  container.classList.remove("right-panel-active");
});

var signInAction = function () {
  // /signin post

  // window.location.href = "/calendar";
  const email = document.getElementById("emailLogin").value;

  const password = document.getElementById("passwordLogin").value;

  const data = {
    email,
    password,
  };

  fetch("/login", {
    method: "POST",
    headers: {
      Accept: "application/json",
      "Content-Type": "application/json",
    },
    body: JSON.stringify(data),
  })
    .then((response) => {
      console.log("TCL  ~ file: signup.js:36 ~ .then ~ response:", response);
    })
    .catch((error) => {
      console.error("Error:", error);
    });

  (async () => {
    const rawResponse = await fetch("/login", {
      method: "POST",
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
      },
      body: JSON.stringify(data),
    });
    const content = await rawResponse.json();

    console.log(content);
  })();
};

const form = document.getElementById("loginForm");

form.addEventListener("submit", function (event) {
  event.preventDefault();

  const email = document.getElementById("emailLogin").value;

  const password = document.getElementById("passwordLogin").value;

  const data = {
    email,
    password,
  };

  // (async () => {
  //   const rawResponse = await fetch("/login", {
  //     method: "POST",
  //     headers: {
  //       Accept: "application/json",
  //       "Content-Type": "application/json",
  //     },
  //     body: JSON.stringify(data),
  //   });
  //   const content = await rawResponse.json();

  //   console.log(content);

  //   //  if (result.equals("redirect:/dashboard")) {
  //   //    return "calendar";
  //   //  } else {
  //   //    return "signup";
  //   //  }

  // })();

  fetch("/login", {
    method: "POST",
    headers: {
      Accept: "application/json",
      "Content-Type": "application/json",
    },
    body: JSON.stringify(data),
  })
    .then((response) => response.text())
    .then((data) => {
      if (data == "calendar") window.location.href = "/calendar";
      else {
        window.location.href = "/access";
      }
    })
    .catch((error) => {
      console.error("Error:", error);
    });
});
