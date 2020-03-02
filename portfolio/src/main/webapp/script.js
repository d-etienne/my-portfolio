// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

/**
 * Adds a random greeting to the page.
 */
function addFunFacts() {
  const funFacts =
      ['Ive been a vegitarian since my 10th grade year of highschool!', 'My favorite movie of all time is Madagascar :)', 'I am originally from Maryland, around the DC area!', 'My favorite food is french fries!'];

  // Pick a random fun fact.
  const funFact = funFacts[Math.floor(Math.random() * funFacts.length)];

  // Add it to the page.
  const factContainer = document.getElementById('funFact-container');
  factContainer.innerText = funFact;
}

/*

function getComments() {
    fetch('/data').then(response => response.json()).then((comments) => {
    const commentList = document.getElementById('comment-spot');
    commentList.innerHTML = '';
    comments.forEach((line) => {
      console.log(line);
      commentList.appendChild(createListElement(line));
    });

    for(let comment in comments){
        var message = createListElement(comments[comment]);
        console.log(message);
        commentList.appendChild(message);
        }
    });
}
*/
async function getComments() {
  const response = await fetch('/data');
  const comments = await response.json();
  const commentList = document.getElementById('comment-spot');
  console.log(commentList);
  commentList.innerHTML = '';
  comments.forEach((line) => {
      console.log(line);
      commentList.appendChild(createListElement(line));
  });
}

function createListElement(text) {
  const liElement = document.createElement('li');
  liElement.innerText = text;
  return liElement;
}

