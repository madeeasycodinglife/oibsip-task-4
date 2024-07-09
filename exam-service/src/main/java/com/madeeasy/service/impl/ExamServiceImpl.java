package com.madeeasy.service.impl;

import com.madeeasy.dto.request.AnswerRequestDTO;
import com.madeeasy.dto.request.ExamRequestDTO;
import com.madeeasy.dto.request.QuestionRequestDTO;
import com.madeeasy.dto.request.submit.AnswerSubmittedRequestDTO;
import com.madeeasy.dto.request.submit.ExamSubmittedRequestDTO;
import com.madeeasy.dto.request.submit.QuestionSubmittedRequestDTO;
import com.madeeasy.dto.response.AnswerResponseDTO;
import com.madeeasy.dto.response.ExamResponseDTO;
import com.madeeasy.dto.response.QuestionResponseDTO;
import com.madeeasy.dto.response.submit.AnswerSubmittedResponseDTO;
import com.madeeasy.dto.response.submit.ExamSubmittedResponseDTO;
import com.madeeasy.dto.response.submit.QuestionSubmittedResponseDTO;
import com.madeeasy.entity.*;
import com.madeeasy.exception.ResourceNotFoundException;
import com.madeeasy.repository.*;
import com.madeeasy.service.ExamService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ExamServiceImpl implements ExamService {

    private final ExamRepository examRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final UserAnswerRepository userAnswerRepository;
    private final UserExamRepository userExamRepository;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private HttpServletRequest request;

    @Override
    public List<ExamResponseDTO> getAllExams() {
        List<Exam> examLists = this.examRepository.findAllNotSubmittedExams();
        return examLists.stream()
                .map(exam -> ExamResponseDTO.builder()
                        .id(exam.getId())
                        .title(exam.getTitle())
                        .duration(exam.getDuration())
                        .questions(exam.getQuestions().stream()
                                .map(question -> QuestionResponseDTO.builder()
                                        .id(question.getId())
                                        .text(question.getText())
                                        .answers(question.getAnswers().stream()
                                                .map(answer -> AnswerResponseDTO.builder()
                                                        .id(answer.getId())
                                                        .text(answer.getText())
                                                        .isCorrect(answer.isCorrect())
                                                        .build()
                                                )
                                                .toList()
                                        )
                                        .build()
                                )
                                .toList()
                        )
                        .build()
                )
                .toList();
    }


    @Override
    public ExamResponseDTO getExamById(Long id) {
        return null;
    }

    @Override
    public void createExam(ExamRequestDTO exam) {
        Exam examEntity = Exam.builder()
                .id("EXM" + UUID.randomUUID().toString())
                .title(exam.getTitle())
                .duration(exam.getDuration())
                .build();
        this.examRepository.save(examEntity);

        for (QuestionRequestDTO questionRequestDTO : exam.getQuestions()) {
            Question questionEntity = Question.builder()
                    .id("QTN" + UUID.randomUUID().toString())
                    .text(questionRequestDTO.getText())
                    .exam(examEntity)
                    .build();
            this.questionRepository.save(questionEntity);

            for (AnswerRequestDTO answerRequestDTO : questionRequestDTO.getAnswers()) {
                Answer answerEntity = Answer.builder()
                        .id("ANS" + UUID.randomUUID().toString())
                        .text(answerRequestDTO.getText())
                        .isCorrect(answerRequestDTO.isCorrect())
                        .question(questionEntity)
                        .build();
                this.answerRepository.save(answerEntity);
            }
        }
    }

    @Override
    public ExamResponseDTO updateExam(Long id, ExamRequestDTO examDetails) {
        return null;
    }

    @Override
    public void deleteExam(Long id) {

    }

    @Override
    public List<ExamResponseDTO> getExamByTitle(String title) {
        List<Exam> examList = this.examRepository.findByTitleIgnoreCase(title);

        return examList.stream()
                .map(exam -> ExamResponseDTO.builder()
                        .id(exam.getId())
                        .title(exam.getTitle())
                        .duration(exam.getDuration())
                        .questions(exam.getQuestions().stream()
                                .map(question -> QuestionResponseDTO.builder()
                                        .id(question.getId())
                                        .text(question.getText())
                                        .answers(question.getAnswers().stream()
                                                .map(answer -> AnswerResponseDTO.builder()
                                                        .id(answer.getId())
                                                        .text(answer.getText())
                                                        .isCorrect(answer.isCorrect())
                                                        .build())
                                                .toList())
                                        .build())
                                .toList())
                        .build())
                .toList();
    }

    @Override
    public ExamResponseDTO getExamByExamId(String examId) {
        Exam foundExam = this.examRepository.findById(examId).orElse(null);
        if (foundExam != null) {
            return ExamResponseDTO.builder()
                    .id(foundExam.getId())
                    .title(foundExam.getTitle())
                    .duration(foundExam.getDuration())
                    .questions(foundExam.getQuestions().stream()
                            .map(question -> QuestionResponseDTO.builder()
                                    .id(question.getId())
                                    .text(question.getText())
                                    .answers(question.getAnswers().stream()
                                            .map(answer -> AnswerResponseDTO.builder()
                                                    .id(answer.getId())
                                                    .text(answer.getText())
                                                    .isCorrect(answer.isCorrect())
                                                    .build())
                                            .toList())
                                    .build())
                            .toList())
                    .build();
        }
        return null;
    }

    @Override
    public void submitAnswer(String examId, ExamSubmittedRequestDTO examSubmitRequestDTO) {


        /** ------------ Algorithm ------------
         * 1. Fetch the exam and user.
         * 2. Validate the fetched data.
         * 3. Iterate through the provided questions and answers.
         * 4. Compare the submitted answers with the correct answers.
         * 5. Calculate the score.
         * 6. Save the results in the `UserExam` entity.
         * 7. Return a response indicating the score and completion status.
         */


        Exam foundExamFromDB = this.examRepository.findById(examId).orElse(null);
        // rest-call to user-service to get user by emailId and will be assigned to vo/User.class

        String authorizationHeader = request.getHeader("Authorization");

        String token = authorizationHeader.substring(7);
        String findByEmailIdUrl = "http://user-service/user-service/" + examSubmitRequestDTO.getEmailId();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<User> response = restTemplate.exchange(findByEmailIdUrl, HttpMethod.GET, entity, User.class);
        User fondUserFromDB = response.getBody();


        if (foundExamFromDB == null || fondUserFromDB == null) {
            throw new ResourceNotFoundException("Exam or user not found");
        }

        // --------------------------- calculate score

        Integer score = calculateScore(examSubmitRequestDTO, foundExamFromDB);


        // create UserExam entity
        UserExam userExam = UserExam.builder()
                .id("UE" + UUID.randomUUID().toString())
                .examTitle(foundExamFromDB.getTitle())
                .userEmail(fondUserFromDB.getEmail())
                .score(score)
                .completed(true)
                .duration(foundExamFromDB.getDuration())
                .durationTaken(examSubmitRequestDTO.getDurationTaken())
                .build();


        Exam exam = Exam.builder()
                .id("SUBMITTED_EXAM" + fondUserFromDB.getId().substring(0, 4) + UUID.randomUUID().toString())
                .title(examSubmitRequestDTO.getTitle())
                .duration(foundExamFromDB.getDuration())
                .questions(foundExamFromDB.getQuestions())
                .build();
        this.examRepository.save(exam);

        userExam.setExam(exam);

        UserExam savedUserExam = this.userExamRepository.save(userExam);
        Question savedQuestion = null;
        UserAnswer savedAnswer = null;
        for (QuestionSubmittedRequestDTO submittedQuestion : examSubmitRequestDTO.getQuestions()) {
            Question question = Question.builder()
                    .id("SUBMITTED_QUESTION" + fondUserFromDB.getId().substring(0, 4) + UUID.randomUUID().toString())
                    .text(submittedQuestion.getText())
                    .exam(exam)
                    .build();
            savedQuestion = this.questionRepository.save(question);

            for (AnswerSubmittedRequestDTO answer : submittedQuestion.getAnswers()) {
                UserAnswer answerToBeSaved = UserAnswer.builder()
                        .id("SUBMITTED_ANSWER" + fondUserFromDB.getId().substring(0, 4) + UUID.randomUUID().toString())
                        .text(answer.getText())
                        .isCorrect(answer.isCorrect())
                        .isSelected(answer.isSelected())
                        .question(question)
                        .build();
                savedAnswer = this.userAnswerRepository.save(answerToBeSaved);
            }
        }

    }

    private Integer calculateScore(ExamSubmittedRequestDTO examSubmitRequestDTO, Exam foundExamFromDB) {
        // calculate the score
        // Initialize score
        int score = 0;

        // Iterate through the provided answers
        for (QuestionSubmittedRequestDTO submittedQuestion : examSubmitRequestDTO.getQuestions()) {
            String questionId = submittedQuestion.getId();

            // Find the corresponding question
            Question filteredQuestionFromDB = foundExamFromDB.getQuestions().stream()
                    .filter(q -> q.getId().equals(questionId))
                    .findFirst()
                    .orElse(null);

            if (filteredQuestionFromDB == null) {
                // Question not found
                return null;
            }

            // Find the corresponding answers
            List<AnswerSubmittedRequestDTO> submittedAnswers = submittedQuestion.getAnswers().stream()
                    .map(a -> AnswerSubmittedRequestDTO.builder()
                            .id(a.getId())
                            .isCorrect(a.isCorrect())
                            .isSelected(a.isSelected())
                            .build())
                    .toList();

            if (submittedAnswers == null) {
                // Answers not found
                return null;
            }

            // Iterate through the provided answers
            for (AnswerSubmittedRequestDTO answer : submittedAnswers) {
                String answerId = answer.getId();

                // Find the corresponding answer
                Answer filteredAnswer = filteredQuestionFromDB.getAnswers().stream()
                        .filter(a -> a.getId().equals(answerId))
                        .findFirst()
                        .orElse(null);
                if (filteredAnswer == null) {
                    // Answer not found
                    return null;
                }
                if (filteredAnswer.isCorrect() && answer.isCorrect() && answer.isSelected()) {
                    score++;
                }
            }
        }
        return score;
    }

    @Override
    public List<ExamSubmittedResponseDTO> getSubmittedAnswer(String examId, String emailId) {
        // rest-call to user-service to get user by emailId and will be assigned to vo/User.class

        String authorizationHeader = request.getHeader("Authorization");

        String token = authorizationHeader.substring(7);
        String findByEmailIdUrl = "http://user-service/user-service/" + emailId;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<User> response = restTemplate.exchange(findByEmailIdUrl, HttpMethod.GET, entity, User.class);
        User foundUserFromDB = response.getBody();


        if (foundUserFromDB == null) {
            return null;
        }

        Exam foundExamFromDB = this.examRepository.findById(examId).orElse(null);
        if (foundExamFromDB == null) {
            return null;
        }
        List<UserExam> userExamList = this.userExamRepository.findByUserEmail(foundUserFromDB.getEmail());
        if (!userExamList.isEmpty()) {
            return userExamList.stream()
                    .map(u -> ExamSubmittedResponseDTO.builder()
                            .userId(u.getId())
                            .examId(u.getExam().getId())
                            .emailId(u.getUserEmail())
                            .title(u.getExam().getTitle())
                            .duration(u.getExam().getDuration())
                            .durationTaken(u.getDurationTaken())
                            .score(u.getScore())
                            .questions(u.getExam().getQuestions().stream()
                                    .map(q -> QuestionSubmittedResponseDTO.builder()
                                            .id(q.getId())
                                            .text(q.getText())
                                            .answers(q.getUserAnswers().stream()
                                                    .map(a -> AnswerSubmittedResponseDTO.builder()
                                                            .id(a.getId())
                                                            .text(a.getText())
                                                            .isCorrect(a.isCorrect())
                                                            .isSelected(a.isSelected())
                                                            .build())
                                                    .toList())
                                            .build())
                                    .toList())
                            .build())
                    .toList();
        }
        return null;
    }

    @Override
    public List<ExamSubmittedResponseDTO> getSubmittedAnswerByEmailId(String emailId) {

        // rest-call to user-service to get user by emailId and will be assigned to vo/User.class

        String authorizationHeader = request.getHeader("Authorization");

        String token = authorizationHeader.substring(7);
        String findByEmailIdUrl = "http://user-service/user-service/" + emailId;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<User> response = restTemplate.exchange(findByEmailIdUrl, HttpMethod.GET, entity, User.class);
        User foundUserFromDB = response.getBody();


        assert foundUserFromDB != null;
        List<UserExam> userExamList = this.userExamRepository.findByUserEmail(foundUserFromDB.getEmail());

        if (!userExamList.isEmpty()) {
            return userExamList.stream()
                    .map(userExam -> ExamSubmittedResponseDTO.builder()
                            .userId(foundUserFromDB.getId())
                            .examId(userExam.getExam().getId())
                            .emailId(emailId)
                            .title(userExam.getExam().getTitle())
                            .duration(userExam.getExam().getDuration())
                            .durationTaken(userExam.getDurationTaken())
                            .score(userExam.getScore())
                            .questions(userExam.getExam().getQuestions().stream()
                                    .map(q -> QuestionSubmittedResponseDTO.builder()
                                            .id(q.getId())
                                            .text(q.getText())
                                            .answers(q.getUserAnswers().stream()
                                                    .map(a -> AnswerSubmittedResponseDTO.builder()
                                                            .id(a.getId())
                                                            .text(a.getText())
                                                            .isCorrect(a.isCorrect())
                                                            .isSelected(a.isSelected())
                                                            .build())
                                                    .toList())
                                            .build())
                                    .toList()
                            )
                            .build()
                    )
                    .toList();
        }
        return null;
    }
}
