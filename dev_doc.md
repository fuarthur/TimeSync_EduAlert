# TimeSync EduAlert Development Document

## Development Guidelines
- The development of TimeSync EduAlert follows the MVVM (Model-View-ViewModel) architecture pattern.
- All development work should be based on the `dev` branch. After testing and code review, it can be merged into the `main` branch.
- Avoid hard-coding character resources in XML, place them in the corresponding XML file.
- All variable names should be meaningful, otherwise, use _ or similar to replace them.
- Minimize the use of magic numbers in code, use constants instead.
- Add comments for complex logic.
- Check for TODOs and errors before committing code. Use standardized git commit messages (refer to the Git Commit Standards below).

## Git Commit Standards
- Use English for commit messages.
- Use the present tense for descriptions.
- Use the imperative mood for instructions.
- Limit the first line to 72 characters or fewer.
- Use bullet points to separate multiple changes within one commit.
- Prefix the commit message with a type annotation, following the standards below.
- Include a brief summary of the changes in the commit message body.

## Git Commit Types
| Category | Description                     |
|----------|---------------------------------|
| feat     | New feature                     |
| fix      | Bug fix                         |
| docs     | Documentation update             |
| style    | Code changes that do not affect logic |
| refactor | Code refactoring                |
| perf     | Performance or user experience optimization |
| test     | New test cases or updates to existing tests |
| build    | Changes to the project's build system |
| ci       | Changes to the project's continuous integration process |
| chore    | Other changes that don't fit into the above categories |
| revert   | Revert to a previous commit      |
