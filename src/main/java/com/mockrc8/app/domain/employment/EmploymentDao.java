package com.mockrc8.app.domain.employment;


import com.mockrc8.app.domain.employment.model.Employment;
import com.mockrc8.app.domain.employment.model.EmploymentImage;
import com.mockrc8.app.domain.employment.model.EmploymentTechSkill;
import com.mockrc8.app.domain.employment.model.TechSkill;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@AllArgsConstructor
public class EmploymentDao {

    private JdbcTemplate jdbcTemplate;

    class EmploymentRowMapper implements RowMapper<Employment>{
        @Override
        public Employment mapRow(ResultSet rs, int rowNum) throws SQLException {
            Employment employment = new Employment(
                    rs.getLong("employment_id"),
                    rs.getLong("company_id"),
                    rs.getString("title"),
                    rs.getString("context"),
                    rs.getTimestamp("deadline").toLocalDateTime(),
                    rs.getString("country"),
                    rs.getString("location"),
                    rs.getInt("referral_compensation"),
                    rs.getInt("user_compensation")
            );

            return employment;
        }
    }

    class EmploymentImageRowMapper implements RowMapper<EmploymentImage>{
        @Override
        public EmploymentImage mapRow(ResultSet rs, int rowNum) throws SQLException {
            EmploymentImage employmentImage = new EmploymentImage(
                    rs.getLong("employment_image_id"),
                    rs.getLong("employment_id"),
                    rs.getLong("image_id")
            );

            return employmentImage;
        }
    }

    class TechSkillRowMapper implements RowMapper<TechSkill>{
        @Override
        public TechSkill mapRow(ResultSet rs, int rowNum) throws SQLException {
            TechSkill techSkill = new TechSkill(
                    rs.getLong("tech_skill_id"),
                    rs.getString("name")
            );

            return techSkill;
        }
    }

    class EmploymentTechSkillRowMapper implements RowMapper<EmploymentTechSkill>{
        @Override
        public EmploymentTechSkill mapRow(ResultSet rs, int rowNum) throws SQLException {
            EmploymentTechSkill employmentTechSkill = new EmploymentTechSkill(
                    rs.getLong("employment_tech_skill_id"),
                    rs.getLong("employment_id"),
                    rs.getLong("tech_skill_id")
            );

            return employmentTechSkill;
        }
    }


    public Integer checkEmploymentListExist(){
        String query = "select if( exists( select * from employment ), 1, 0)";

        return this.jdbcTemplate.queryForObject(
                query,
                Integer.class
        );
    }

    /*
    인자와 employment_id가 일치하는 employment가 있는지 체크
     */
    public Integer checkEmploymentId(Long employmentId){
        String query = "select if( exists( select * from employment where employment_id = ? ), 1, 0)";

        return this.jdbcTemplate.queryForObject(
                query,
                Integer.class,
                employmentId
        );
    }

    /*
    employment 의 속성인 company_id에 대한 체크 메서드를 작성하지 않는 이유
    -> 어떠한 Company가 아직 채용 공고를 하나도 등록하지 않았을 수 있음
     */

    public Long registerEmployment(Employment employment){
        String query = "insert into employment" +
                " (company_id, title, context, deadline, country, location, referral_compensation, user_compensation )" +
                " values (?, ?, ?, ?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        this.jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement pstmt = con.prepareStatement(query, new String[]{"employment_id"});
                pstmt.setLong(1, employment.getCompanyId());
                pstmt.setString(2, employment.getTitle());
                pstmt.setString(3, employment.getContext());
                pstmt.setTimestamp(4, Timestamp.valueOf(employment.getDeadline()));
                pstmt.setString(5, employment.getCountry());
                pstmt.setString(6, employment.getLocation());
                pstmt.setInt(7, employment.getReferralCompensation());
                pstmt.setInt(8, employment.getUserCompensation());

                return pstmt;
            }
        }, keyHolder);

        Number key = keyHolder.getKey();
        return key.longValue();
    }


    /*
    모든 채용 조회
     */
    public List<Employment> getEmploymentList(){
        String query = "select * from employment";

        return this.jdbcTemplate.query(
                query,
                new EmploymentRowMapper()
        );
    }


    /*
    employment_id로 특정 채용 조회
     */
    public Employment getEmploymentById(Long employmentId){
        String query = "select * from employment where employment_id = ?";

        return this.jdbcTemplate.queryForObject(
                query,
                new EmploymentRowMapper(),
                employmentId
        );
    }


    /*
    company_id로 채용 목록 조회
     */
    public List<Employment> getEmploymentListByCompanyId(Long companyId){
        String query = "select * from employment where company_id = ?";

        return this.jdbcTemplate.query(
                query,
                new EmploymentRowMapper(),
                companyId
        );
    }


    /*
    company_tag_id로 해당 태그를 가지는 기업 목록 조회
     */

    public List<Employment> getEmploymentListByCompanyTagName(String companyTagName, Long employmentId, Integer count){

        String query = "select * from employment where company_id in (select company_id from company_tag where hashtag_name like ?) and employment_id != ? limit ?";
        String param = "%" + companyTagName + "%";

        return this.jdbcTemplate.query(
                query,
                new EmploymentRowMapper(),
                param,
                employmentId,
                count
        );
    }



    /*
    employment_image
     */

    public List<EmploymentImage> getEmploymentImageListByCompanyId(Long employmentId){
        String query = "select * from employment_image where employment_id = ?";

        return this.jdbcTemplate.query(
                query,
                new EmploymentImageRowMapper(),
                employmentId
        );
    }


    /*
    tech_skill
     */

    public TechSkill getTechSkillById(Long techSkillId){
        String query = "select * from tech_skill where tech_skill_id = ?";

        return this.jdbcTemplate.queryForObject(
                query,
                new TechSkillRowMapper(),
                techSkillId
        );
    }


    /*
    employment_tech_skill
     */

    public Integer checkEmploymentTechSkillByEmploymentId(Long employmentId){
        String query = "select if( exists( select * from employment_tech_skill where employment_id = ?), 1, 0)";

        return this.jdbcTemplate.queryForObject(
                query,
                Integer.class,
                employmentId
        );
    }
    public List<EmploymentTechSkill> getEmploymentTechSkillListByEmploymentId(Long employmentId){
        String query = "select * from employment_tech_skill where employment_id = ?";

        return this.jdbcTemplate.query(
                query,
                new EmploymentTechSkillRowMapper(),
                employmentId
        );
    }


}
