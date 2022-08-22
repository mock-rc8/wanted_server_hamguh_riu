package com.mockrc8.app.domain.company;

import com.mockrc8.app.domain.company.model.*;
import com.mockrc8.app.domain.employment.EmploymentDao;
import com.mockrc8.app.domain.employment.model.Employment;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@AllArgsConstructor
public class CompanyDao {

    private JdbcTemplate jdbcTemplate;

    class CompanyRowMapper implements RowMapper<Company>{
        @Override
        public Company mapRow(ResultSet rs, int rowNum) throws SQLException {
            Company company = new Company(
                    rs.getLong("company_id"),
                    rs.getString("image_url"),
                    rs.getString("name"),
                    rs.getString("description")
            );

            return company;
        }
    }

    class CompanyTagRowMapper implements RowMapper<CompanyTag>{
        @Override
        public CompanyTag mapRow(ResultSet rs, int rowNum) throws SQLException {
            CompanyTag companyTag = new CompanyTag(
                    rs.getLong("hashtag_id"),
                    rs.getLong("company_id"),
                    rs.getString("hashtag_name")
            );

            return companyTag;
        }
    }

    class CompanyImageRowMapper implements RowMapper<CompanyImage>{
        @Override
        public CompanyImage mapRow(ResultSet rs, int rowNum) throws SQLException {
            CompanyImage companyImage = new CompanyImage(
                    rs.getLong("company_image_id"),
                    rs.getLong("company_id"),
                    rs.getLong("image_id")
            );

            return companyImage;
        }
    }


    class ImageRowMapper implements RowMapper<Image>{
        @Override
        public Image mapRow(ResultSet rs, int rowNum) throws SQLException {
            Image image = new Image(
                    rs.getLong("image_id"),
                    rs.getString("image_url")
            );

            return image;
        }
    }

    class CompanyNewsRowMapper implements RowMapper<CompanyNews>{
        @Override
        public CompanyNews mapRow(ResultSet rs, int rowNum) throws SQLException {
            CompanyNews companyNews = new CompanyNews(
                    rs.getLong("company_news_id"),
                    rs.getLong("company_id"),
                    rs.getString("news_link")
            );

            return companyNews;
        }
    }

    class CompanyEmploymentRowMapper implements RowMapper<Employment>{
        @Override
        public Employment mapRow(ResultSet rs, int rowNum) throws SQLException {
            Employment employment = new Employment(
                    rs.getLong("company_id"),
                    rs.getString("title"),
                    rs.getTimestamp("deadline").toLocalDateTime(),
                    rs.getInt("referral_compensation"),
                    rs.getInt("user_compensation")
            );

            return employment;
        }
    }

    /*
    Company가 하나도 없는지 체크
     */
    public Integer checkCompanyListExist(){
        String query = "select if( exists( select * from company ), 1, 0)";

        return this.jdbcTemplate.queryForObject(
                query,
                Integer.class
        );
    }

    public Integer checkCompanyId(Long companyId){
        String query = "select if( exists( select * from company where company_id = ?), 1, 0)";

        return this.jdbcTemplate.queryForObject(
                query,
                Integer.class,
                companyId
        );
    }


    /*
    Company 생성
     */
    public Long registerCompany(Company company){
        String query = "insert into company (image_url, name, description) values (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        this.jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement pstmt = con.prepareStatement(query, new String[]{"company_id"});
                pstmt.setString(1, company.getImageUrl());
                pstmt.setString(2, company.getName());
                pstmt.setString(3, company.getDescription());

                return pstmt;
            }
        }, keyHolder);

        Number key = keyHolder.getKey();
        return key.longValue();
    }

    public List<Company> getCompanyList(){
        String query = "select * from company";

        return this.jdbcTemplate.query(query, new CompanyRowMapper());
    }

    public Company getCompanyById(Long companyId){
        String query = "select * from company where company_id = ?";

        return this.jdbcTemplate.queryForObject(query, new CompanyRowMapper(), companyId);
    }


    /*
   image 기본 crud
     */

    public Integer checkImageId(Long imageId){
        String query = "select if( exists( select * from image where image_id = ? ), 1, 0)";

        return this.jdbcTemplate.queryForObject(
                query,
                Integer.class,
                imageId
        );
    }

    public Image getImageById(Long imageId){
        String query = "select * from image where image_id = ?";

        return this.jdbcTemplate.queryForObject(query,
                new ImageRowMapper(),
                imageId
        );
    }

    public Long registerImage(Image image){
        String query = "insert into image ( image_url ) values ( ? )";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        this.jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement pstmt = con.prepareStatement(query, new String[]{"image_id"});
                pstmt.setString(1, image.getImageUrl());

                return pstmt;
            }
        }, keyHolder);

        Number key = keyHolder.getKey();
        return key.longValue();
    }


    /*
    company_image
     */

    public Integer checkCompanyImageId(Long companyImageId){
        String query = "select if( exists( select * from company_image where company_image_id = ? ), 1, 0)";

        return this.jdbcTemplate.queryForObject(
                query,
                Integer.class,
                companyImageId
        );
    }

    public Integer checkCompanyImageByCompanyId(Long companyId){
        String query = "select if( exists( select * from company_image where company_id = ?), 1, 0)";

        return this.jdbcTemplate.queryForObject(
                query,
                Integer.class,
                companyId
        );
    }

    public Long registerCompanyImage(CompanyImage companyImage){
        String query = "insert into company_image ( company_id, image_id ) values (?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        this.jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement pstmt = con.prepareStatement(query, new String[]{"company_image_id"});
                pstmt.setLong(1, companyImage.getCompanyId());
                pstmt.setLong(2, companyImage.getImageId());

                return pstmt;
            }
        }, keyHolder);

        Number key = keyHolder.getKey();
        return key.longValue();
    }

    public List<CompanyImage> getCompanyImageListByCompanyId(Long companyId){
        String query = "select * from company_image where company_id = ?";

        return this.jdbcTemplate.query(
                query,
                new CompanyImageRowMapper(),
                companyId
        );
    }


    /*
    company_tag 기본 crud
     */

    public Integer checkCompanyTagExist(){
        String query = "select if( exists( select * from company_tag), 1, 0)";

        return this.jdbcTemplate.queryForObject(
                query,
                Integer.class
        );
    }

    public Integer checkCompanyTagByCompanyId(Long companyId){
        String query = "select if( exists( select * from company_tag where company_id = ? ), 1, 0)";

        return this.jdbcTemplate.queryForObject(
                query,
                Integer.class,
                companyId
        );
    }

    public Integer checkCompanyTagId(Long companyTagId){
        String query = "select if( exists( select * from company_tag where hashtag_id = ?), 1, 0)";

        return this.jdbcTemplate.queryForObject(
                query,
                Integer.class,
                companyTagId
        );
    }

    public Long registerCompanyTag(CompanyTag companyTag){
        String query = "insert into company_tag (company_id, hashtag_name) values(?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        this.jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement pstmt = con.prepareStatement(query, new String[] {"hashtag_id"});
                pstmt.setLong(1, companyTag.getCompanyId());
                pstmt.setString(2, companyTag.getCompanyTagName());

                return pstmt;
            }
        }, keyHolder);

        Number key = keyHolder.getKey();
        return key.longValue();
    }

    public List<CompanyTag> getCompanyTagList(){
        String query = "select * from company_tag";

        return this.jdbcTemplate.query(
                query,
                new CompanyTagRowMapper()
        );
    }

    public List<CompanyTag> getCompanyTagListByCompanyId(Long companyId){
        String query = "select * from company_tag where company_id = ?";

        return this.jdbcTemplate.query(
                query,
                new CompanyTagRowMapper(),
                companyId
        );
    }

    public CompanyTag getCompanyTagById(Long companyTagId){
        String query = "select * from company_tag where hashtag_id = ?";

        return this.jdbcTemplate.queryForObject(
                query,
                new CompanyTagRowMapper(),
                companyTagId
        );
    }


    /*
    company_news
     */
    public List<CompanyNews> getCompanyNewsListByCompanyId(Long companyId){
        String query = "select * from company_news where company_id = ?";

        return this.jdbcTemplate.query(
                query,
                new CompanyNewsRowMapper(),
                companyId
        );
    }


    /*
    목록에서 보여 줄 employment
     */
    public List<Employment> getEmploymentListByCompanyId(Long companyId){
        String query = "select * from employment where company_id = ?";

        return this.jdbcTemplate.query(
                query,
                new CompanyEmploymentRowMapper(),
                companyId
        );
    }

}
