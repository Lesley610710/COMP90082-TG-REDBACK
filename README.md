# TG-Redback Overview

Automated reading test sendText generation

The Language Testing Research Centre in the School of Languages and Linguistics develops reading tests to assess the language proficiency of students transferring from high school to university.  

We are seeking help with the development of reading texts to be used in such tests. We are hoping that a software can be developed that draws on ChatGPT to help us develop the texts for reading tests, generating texts on different topics.

# Background
Language Testing Research Centre (LTRC)
Established in 1990, the Language Testing Research Centre (LTRC) at the University of Melbourne (Unimelb) has become an international leader in research and development in language assessment and language program evaluation. The history of the LTRC can be found here. 

The LTRC at Unimelb has successfully demonstrated their professionality to their clients. To date, their clients have been:

    - Educational Testing Service (Princeton, NJ)
    - University of Cambridge Local Examinations Syndicate (Cambridge, UK)
    - South East Asia Ministers of Education Organisation (Singapore),
    - OECD (Paris); and
    - Other agencies across the world

Language Testing and Assessment
The main research area of the LTRC is related to the language testing and assessment. This is a discipline with a principled approach to designing and validating tools to assess and evaluate language abilities. The language testing field is informed by the academic disciplines of linguistics, the social sciences and education. The practice of language assessment supports just/sound decision making for policy makers, practitioners and test takers in education, migration and workplace contexts.
LTRC mission

The LTRC serves as a global leader promoting ethical and exemplary research and practice in language testing and assessment. Their aim is to build professional expertise and public understanding of the field and to advocate for appropriate testing practices on behalf of stakeholders. They provide consultancy services, policy advice and public advocacy.
LTRC tasks

To accomplish their mission, the LTRC carries out the following detailed tasks:

    - carry out research in language testing and assessment.
    - develop and validate tests and other tools to measure abilities in English and other languages, for various purposes such as:
        diagnosis of learning needs
        placement into language programs
        course admissions
        program evaluation
        certification of language abilities for professional / employment needs
    - provide education and training in the area of language testing and assessment
    - provide policy advice and advocate for appropriate testing and assessment practices

LTRC ambition
One of the key responsibilities of the LTRC is to develop Language Reading Tests (LRTs). These assessments are essential for evaluating the language proficiency of incoming university students. Recognizing the capabilities and potential of GPT models, the team is keen to integrate them into their workflow. By doing so, they aim to streamline and enhance the process of generating topics and texts for these reading tests. Leveraging the power of GPT models will not only improve the efficiency of language test development but also allow the team to focus more on researching the fundamental aspects of language assessment.


# Motivation

Primary users of the system will be the reading test developers (RTDs). They would use this system intensively to help them with their development process for language reading tests, aka topic and sendText generation (TATG). Their current 3-step development process begins with preparing a list of topics that are suitable for target test takers, ranging from high school students to adults (i.e. international students at Master level). Then, with topics decided, the contextual reading texts will be developed by writers in LTRC. The reading texts are carefully designed with several technical requirements, such as genre, length, difficulty and inclusion of convincing distractors, etc. For a detailed requirement on the sendText, please refer to the initial client document. Finally, the generated sendText are used for developing questions like multiple choices, short/long answers, or even more ambitious... for the entire AU project (later can change this link to their confluence work space).

Like all other organisations, the LTRC is eager to improve their working efficiency. At the moment, the bottle neck lies in the sendText development process. Even for a very experienced sendText writer in LTRC, this step can still take some time. On recognising the power and potential of the GPT models, the LTRC decided to build their own GPT system to help with reading sendText development.

# Project Team
| Name   |  Role  | Email |
| ------ | ------ | ------ |
| Ivy Chen | Client | uknoch@unimelb.edu.au |
| Stephanie Brook | Project Supervisor | sbbrook@unimelb.edu.au |
| Chuansheng Zhou | Product Owner | chuanshengz@student.unimelb.edu.au |
| Yakang Niu | Scrum Master | yakangn@student.unimelb.edu.au |
| Yiling Lu | Front-end Architect | yilingl6@student.unimelb.edu.au |
| Yuchen Zhang | Back-end Architect | yuczhang8@student.unimelb.edu.au |
| Yutong Wang | User Researcher | yutwang7@student.unimelb.edu.au |

# Product Deployment
### Link:
https://tg-backend-397705.web.app/user/login
### Tech Details:
| | Frontend | Backend |
| ---- | ---- | ---- |
| Deploy location | Google Firebase | AWS EC2 Instance |
| Instance OS | NA | Ubuntu 22.04.3 LTS |
| Public IP | 199.36.158.100 | 3.106.206.48 |
| Domain | https://tg-backend-397705.web.app/ | https://api.tg-redback.com |
| SSL/TLS Certificate Issuer | AWS Certificate Manager | Let's Encrypt |

# A Note on ChatGPT implementation
Please be noted that to use ChatGPT API, it will require available credit in your OpenAI account, and this is different from the ChatGPT subscription. You will need to pay them separately and will be able to obtain the ChatGPT API Key from your OpenAI account. 
### For future development teams:
Our clients may not wish to share their OpenAI account with us. You may need to register a new OpenAI account for future development. Good news is that newly opened account will come with a $15 free credit. Bad news is that as the ChatGPT API becomes more and more popular, in your semester, this may not be the case anymore...

Also please take a note that every time when you use the ChatGPT API to get something, it comes with a cost. For a detailed pricing, please find the following link:

https://openai.com/pricing

Another good news is that different model will have different cost. If you wish to change the ChatGPT API Key or the model nomination in our source, you can do that in this class:

src/main/java/com.example.server/service/Creator.class

# Future Development
Our solution for the client is built around the implementation of a GPT model deployed locally. Given our tight timeline this semester, we've structured the development process into three distinct stages. We're proud to announce the successful completion of stage 1, establishing a solid foundation for the project. While there's potential for further refinements and features in this stage, stages 2 and 3 have been designed to capture the broader vision of this initiative.

As our team concludes our tenure this semester, we're confident in the groundwork we've laid out. The baton will now be passed to the subsequent teams, who will embark on the next phases. Stages 2 and 3 promise advanced functionalities and integrations, elevating the solution to its envisioned form. We're excited to see the future evolution of this project and trust that the following teams will continue to build on our initial momentum, delivering a product that stands testament to our collective efforts.
![image](https://github.com/COMP90082-2023-SM2/TG-Redback/assets/142386465/126fb985-9e04-4cdf-995d-d3c2c561a332)



# Sprint 1 changelog
- Merge remote-tracking branch 'origin/sprint_1'
- edit readme file
- update new version user story
- Delete User Story.pdf
- edit readme file
- update user story and sprint 1 branch
- Update README.md
- Update changelog
- update personas
- update personas
- Delete personas.pdf
- update personas
- Delete Personas.pdf
- Add files via upload
- update user story
- update background
- update docs
- Merge remote-tracking branch 'origin/main'
- update docs
- Update README.md
- Update README.md
- Update README.md
- Update README.md
- Update README.md
- SpringBoot framework
- SpringBoot framework
- push test
- Update README.md
- Update README.md
- Initial commit

# Sprint 2 changelog
- Merge pull request #1 from COMP90082-2023-SM2/sprint_2 
- Merge branch 'main' into sprint_2 
- Added Generated Topics pages (redirect to this page after hitting generate button on Topic page)
- Merge branch 'Story-8-User-Management' into sprint_2 
- non-admin user cannot user router to access admin pages 
- non-admin user restrictions 
- User edit profile 
- maintenance pages for user profile edit and edit sendText, user profile image updated, user can no longer delete themselves in the userlist 
- admin edit input fields validation 
- admin register input fields validations 
- Login input fields validations 
- Edit users, connected to backend 
- register user and remove user, connected with backend 
- fixed admin user list 
- synchronize 
- Merge branch 'Story-8-User-Management' into sprint_2
- admin register user front end development
- Merge branch 'set-up' into sprint_2
- display user profile name
- Merge branch 'Story-8-User-Management' into sprint_2
- connect backend and fetch user data
- Merge remote-tracking branch 'origin/sprint_2' into sprint_2
- Add configuration-process dependency & fix getUserList function
- Fetch sample data from backend
- Admin user list
- Merge branch 'Story-2-Login' into sprint_2
- remove stashed
- Merge branch 'Story-2-Login' into sprint_2
- Login authentication and connection with backend
- Merge branch 'Story-4-Generate-Topic-List' into sprint_2
- Topic side card size
- Merge branch 'set-up' into sprint_2
- Incorporate established branding elements
- Merge branch 'set-up' into sprint_2
- Deployed front end to dev server
- try fix cors bug
- add Cors configuration
- add port edit function
- Change DB File format and alternating
- Merge remote-tracking branch 'origin/sprint_2' into sprint_2
- Configure Communication protocols
- Merge branch 'Story-2-Login' into sprint_2
- Change login route
- Merge branch 'Story-2-Login' into sprint_2
- Sent login credentials in json form to request from backend
- Merge branch 'set-up' into sprint_2
- Incorporate established branding elements
- delete & update
- Modify Controller and JsonFileOperator functions
- Topic List side card (search, add new topics, remove topics, sort topics, select topics)
- Topic page (suggest topics, enter topic idea field, clear button, generate topics button))
- Incorporate established branding elements
- Set up default vertical navigation layout
- Apply Design Class Model
- Email and password field validation
- Incorporate established branding elements
- Login Page
- template initialization
- Upload front end template
- Create front end folder
- Controller & DB file development


#  Sprint 3 changelog
- Merge branch 'Story-2A-Generate-Topics' into sprint_3
- generate topic by manual input
- Merge branch 'Story-2A-Generate-Topics' into sprint_3
- change suggested topics api path
- Merge branch 'Story-2C-Generate-Texts' into sprint_3
- text page can fetch topic list to choose
- Merge branch 'Story-2A-Generate-Topics' into sprint_3
- topic list, fetch, add, remove
- topic list, fetch, add, remove
- Merge branch 'fixbug' into sprint_3
- fix jar packaging issue
- generate topic fix bugs
- fixed topic & text add/remove/getAll features
- Merge remote-tracking branch 'origin/sprint_3' into sprint_3
- commit local change
- commit local change
- update text & topic add/remove/getAll features
- enable text & topic add/remove/getAll features
- remove forget password function
- Merge branch 'Story-2A-Generate-Topics' into sprint_3
- add loading component to suggested topic when generation is not yet done
- Merge branch 'Story-2C-Generate-Texts' into sprint_3
- text generation connected with backend
- Merge branch 'Story-2A-Generate-Topics' into sprint_3
- Suggested topics connected with backend
- update text feature connection to front end
- update topic feature connection to front end
- Merge pull request #8 from COMP90082-2023-SM2/US.2A-GenerateTopic
- Merge branch 'sprint_3' into US.2A-GenerateTopic
- US.2A-GenerateTopic: Merge Revert, solve conflicts
- Merge pull request #6 from COMP90082-2023-SM2/revert-5-US.2A-GenerateTopic
- Revert "Merge US.2A-GenerateTopic into sprint_3"
- Merge remote-tracking branch 'origin/sprint_3' into sprint_3
- to merge
- Merge pull request #5 from COMP90082-2023-SM2/US.2A-GenerateTopic
- Merge branch 'sprint_3' into US.2A-GenerateTopic
- UCT.2A.2-2: Resolve merge issues
- Merge pull request #4 from COMP90082-2023-SM2/revert-3-US.2A-GenerateTopic
- Revert "Merge US.2A-Generate Topic to sprint_3"
- Merge pull request #3 from COMP90082-2023-SM2/US.2A-GenerateTopic
- Merge branch 'sprint_3' into US.2A-GenerateTopic
- UCT.2A.2-2 update gitignore
- UCT.2A.2-2: Use GPT API to get a topic list
- Merge branch 'Story-2C-Generate-Texts' into sprint_3
- text area that are editable, with export and features
- Merge branch 'Story-2C-Generate-Texts' into sprint_3
- generated text ui page with retrieving information from previous page
- Merge branch 'Story-2C-Generate-Texts' into sprint_3
- fix merge
- Merge branch 'Story-2C-Generate-Texts' into sprint_3
- Change ui layout for text generation
- Merge branch 'Story-2C-Generate-Texts' into sprint_3
- Pass topic list item to the input field
- Merge branch 'Story-2A-Generate-Topics' into sprint_3
- Pass the user input for topic generation to the generated page
- fix page ui after merge
- Merge branch 'Story-2A-Generate-Topics' into sprint_3
- generated topics rendered page
- refactor: ignore all IDE based setup files
- Fixed issues during merge
- Merge branch 'Story-1A' into sprint_3
- Fixed issues during merge
- for merge onto sprint_3
- change Creator to adapt API connection
- rebase 2c-text onto sprint_3 branch
- Fix edit and edit profile password hashing issue
- Password hashing (edit, edit profile)
- Develop logic to check for duplicate email addresses to prevent conflicts.
- Merge branch 'User-Story-1-(A,B,C,D)' into sprint_3
- Fix edit and edit profile password hashing issue
- Merge branch 'User-Story-1-(A,B,C,D)' into sprint_3
- Password hashing (edit, edit profile)
- Merge branch 'User-Story-1-(A,B,C,D)' into sprint_3
- Password hashing
- Merge branch 'Story-2A' into sprint_3
- Fix input field validation
- Merge branch 'Story-2C' into sprint_3
- Text generation page input fields validations
- Topic generation page input fields validations
- Text Generation UI
- Input fields validation for US1A, 1B, 1C, 1D
- Add files via upload
- Update README.md
- update main branch docs

