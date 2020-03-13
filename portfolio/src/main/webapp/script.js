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

// fetches the comments from the /data page and displays them on the /index.html
function getComments() {
  fetch('/data').then(response => response.json()).then((comments) => {

    const commentsListElement = document.getElementById('comment-spot');
    commentsListElement.innerHTML = '';
    commentsListElement.appendChild(
        createListElement('Name: ' + comments["Name"]));
    commentsListElement.appendChild(
        createListElement('Comment: ' + comments["Comment"]));
    commentsListElement.appendChild(
        createListElement('Sentiment Score: ' + comments["Sentiment Score"]));
  });
}

function createListElement(text) {
  const liElement = document.createElement('li');
  liElement.innerText = text;
  return liElement;
}

