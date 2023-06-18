var counterDef = 1;
var definition = document.getElementById("definition");
definition.onchange = function () {
  if (definition.lastChild.value != '') {
    counterDef++;
    const id = "def" + (counterDef);
    const label = document.createElement("label");
    label.setAttribute("for", id);
    label.innerHTML = "Definition" + (counterDef);
    const input = document.createElement("input");
    input.setAttribute("type", "text");
    input.setAttribute("class", "form-control");
    input.setAttribute("id", id);
    input.setAttribute("name", id);
    input.setAttribute("placeholder", "Enter definition");
    definition.appendChild(label);
    definition.appendChild(input);
  }
};


var counterSyn = 1;
var alternative = document.getElementById("alternative");
alternative.onchange = function () {
  if (alternative.lastChild.value != '') {
    counterSyn++;
    const id = "syn" + (counterSyn);
    const divTag = document.createElement("div");
    divTag.setAttribute("class","col-2");
    const input = document.createElement("input");
    input.setAttribute("type", "text");
    input.setAttribute("class", "form-control");
    input.setAttribute("id", id);
    input.setAttribute("name", id);
    input.setAttribute("placeholder", "Synonym " + counterSyn);
    divTag.appendChild(input);
    alternative.appendChild(divTag);
  }
};

var counterSen = 1;
var sentenceField = document.getElementById("sentence");
sentenceField.onchange = function () {
  if (sentenceField.lastChild.value != '') {
    counterSen++;
    const id = "sen" + (counterSen);
    const label = document.createElement("label");
    label.setAttribute("for", id);
    label.innerHTML = "Sentence" + (counterSen);
    const input = document.createElement("input");
    input.setAttribute("type", "text");
    input.setAttribute("class", "form-control");
    input.setAttribute("id", id);
    input.setAttribute("name", id);
    input.setAttribute("placeholder", "Enter sentence");
    sentenceField.appendChild(label);
    sentenceField.appendChild(input);
  }
};


