import React from "react";

const ListRepositories = ({ repos }) => {
  return (
    <ul className="repos-list">
      {repos.length > 0
        ? repos.map((repo, key) => (
            <li className="repos-item" key={key}>
              <a
                className="repos-link"
                href={repo.html_url}
                target="_blank"
                rel="noopener noreferrer"
              >
                {repo.name}
              </a>
            </li>
          ))
        : null}
    </ul>
  );
};

export default ListRepositories;
