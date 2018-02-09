package br.com.iandev.midiaindoor.model;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Locale;

import br.com.iandev.midiaindoor.core.integration.IntegrationApi;
import br.com.iandev.midiaindoor.core.interfaces.UpdatebleException;
import br.com.iandev.midiaindoor.util.FileUncompressorUtil;
import br.com.iandev.midiaindoor.util.Files;
import br.com.iandev.midiaindoor.util.JSONUtil;

/**
 * Created by Lucas on 07/12/2016.
 * Changes:
 * Date        Responsible     Change
 * 07/12/2016  Lucas
 */

public class Content extends Model<Content> implements br.com.iandev.midiaindoor.core.interfaces.Content {
    private String description;
    private Character status;

    private Long durationInterval;
    private Date lastPlaybackDate;

    private Long updateInterval;
    private Long updateToleranceInterval;
    private Date lastUpdateDate;
    private Date lastUpdateAttemptDate;

    private Date startDate;
    private Date endDate;

    private IntegrationApi integrationApi;
    private String accessUrl;
    private String hash;
    private String alias;
    private String indexFilePath;

    public Content(Long id) {
        super(id);
    }

    @Override
    public Content parse(JSONObject jsonObject) {
        Content model = new Content(null);
        try {
            model.setId(JSONUtil.getLong(jsonObject, "id"));
            model.setDescription(JSONUtil.getString(jsonObject, "description"));
            model.setStatus(JSONUtil.getCharacter(jsonObject, "status"));

            model.setDurationInterval(JSONUtil.getLong(jsonObject, "durationInterval"));
            model.setLastPlaybackDate(JSONUtil.getDate(jsonObject, "lastPlaybackDate"));

            model.setUpdateInterval(JSONUtil.getLong(jsonObject, "updateInterval"));
            model.setUpdateToleranceInterval(JSONUtil.getLong(jsonObject, "updateToleranceInterval"));
            model.setLastUpdateDate(JSONUtil.getDate(jsonObject, "lastUpdateDate"));
            model.setLastUpdateAttemptDate(JSONUtil.getDate(jsonObject, "lastUpdateAttemptDate"));

            model.setStartDate(JSONUtil.getDate(jsonObject, "startDate"));
            model.setEndDate(JSONUtil.getDate(jsonObject, "endDate"));

            model.setIntegrationApi(IntegrationApi.fromValue(JSONUtil.getString(jsonObject, "integrationApi")));
            model.setAccessUrl(JSONUtil.getString(jsonObject, "accessUrl"));
            model.setHash(JSONUtil.getString(jsonObject, "hash"));
            model.setAlias(JSONUtil.getString(jsonObject, "alias"));
            model.setIndexFilePath(JSONUtil.getString(jsonObject, "indexFilePath"));

        } catch (Exception ex) {
        }
        return model;
    }

    @Override
    public void clearFiles() {
        try {
            Files.deleteDirectory(this.getBaseDirectory());
        } catch (Exception ex) {
            // Silence is golden
        }
    }

    @Override
    public File getPackageFile() {
        return new File(getBaseDirectory(), String.format(Locale.getDefault(), "%018d.zip", getId()));
    }

    @Override
    public File getBaseDirectory() {
        return Files.getDirectory(Files.getManageableDirectory(), String.format(Locale.getDefault(), "%018d", getId()));
    }

    @Override
    public boolean uncompressPackage() throws IOException {
        FileUncompressorUtil.unzip(getPackageFile(), getBaseDirectory());
        return this.getPackageFile().delete();
    }

    @Override
    public String getURLForPlayer() {
        return "file://" + new File(getBaseDirectory().getAbsolutePath(), getIndexFilePath()).getAbsolutePath();
    }

    @Override
    public boolean mustUpdate(Date date) {
        // Status must be active
        if (!Character.valueOf('A').equals(this.getStatus())) {
            return false;
        }

        try {
            Long updateInterval = this.getUpdateInterval();
            if (updateInterval != null && updateInterval < 0L) {
                return false;
            } else {
                return this.getNextUpdateDate().getTime() <= date.getTime();
            }
        } catch (Exception ex) {
            return true;
        }
    }

    @Override
    public boolean outOfDate(Date date) {
        try {
            if (!this.mustUpdate(date)) {
                return false;
            }

            Long updateToleranceInterval = this.getUpdateToleranceInterval();

            if (updateToleranceInterval == null || updateToleranceInterval < 0L) {
                return false;
            }

            long diff = this.getLastUpdateDate().getTime() - date.getTime();
            if (diff < 0L) {
                diff = -diff;
            }

            return updateToleranceInterval < diff - this.getUpdateInterval();

        } catch (Exception ex) {
            return true;
        }
    }

    @Override
    public boolean canPlay(Date date) {
        if (!Character.valueOf('A').equals(this.getStatus())) {
            return false;
        }

        if (this.getDurationInterval() == null || this.getDurationInterval() < 0L) {
            return false;
        }

        if (this.getLastUpdateDate() == null) {
            return false;
        }

        if (this.getStartDate() != null && this.getStartDate().getTime() > date.getTime()) {
            return false;
        }

        if (this.getEndDate() != null && this.getEndDate().getTime() < date.getTime()) {
            return false;
        }

        return !this.outOfDate(date);
    }

    @Override
    public Date getNextUpdateDate() throws UpdatebleException {
        if (this.getLastUpdateDate() == null) {
            throw new UpdatebleException("lastUpdateDate must not be null");
        }

        Long updateInterval = this.getUpdateInterval();
        if (updateInterval == null || updateInterval < 0L) {
            throw new UpdatebleException("updateInterval must be greater than 0");
        }

        return new Date(this.getLastUpdateDate().getTime() + updateInterval);
    }

    // Getters and Setters

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public Character getStatus() {
        return status;
    }

    public void setStatus(Character status) {
        this.status = status;
    }

    @Override
    public Long getDurationInterval() {
        return durationInterval;
    }

    @Override
    public void setDurationInterval(Long durationInterval) {
        this.durationInterval = durationInterval;
    }

    @Override
    public Date getLastPlaybackDate() {
        return lastPlaybackDate;
    }

    @Override
    public void setLastPlaybackDate(Date lastPlaybackDate) {
        this.lastPlaybackDate = lastPlaybackDate;
    }

    @Override
    public Long getUpdateInterval() {
        return updateInterval;
    }

    @Override
    public void setUpdateInterval(Long updateInterval) {
        this.updateInterval = updateInterval;
    }

    @Override
    public Long getUpdateToleranceInterval() {
        return updateToleranceInterval;
    }

    @Override
    public void setUpdateToleranceInterval(Long updateToleranceInterval) {
        this.updateToleranceInterval = updateToleranceInterval;
    }

    @Override
    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    @Override
    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    @Override
    public Date getLastUpdateAttemptDate() {
        return lastUpdateAttemptDate;
    }

    @Override
    public void setLastUpdateAttemptDate(Date lastUpdateAttemptDate) {
        this.lastUpdateAttemptDate = lastUpdateAttemptDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public IntegrationApi getIntegrationApi() {
        return integrationApi;
    }

    public void setIntegrationApi(IntegrationApi integrationApi) {
        this.integrationApi = integrationApi;
    }

    @Override
    public String getAccessUrl() {
        return accessUrl;
    }

    public void setAccessUrl(String accessUrl) {
        this.accessUrl = accessUrl;
    }

    @Override
    public String getHash() {
        return hash;
    }

    @Override
    public void setHash(String hash) {
        this.hash = hash;
    }

    @Override
    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Override
    public String getIndexFilePath() {
        return indexFilePath;
    }

    @Override
    public void setIndexFilePath(String indexFilePath) {
        this.indexFilePath = indexFilePath;
    }

    @Override
    public String toString() {
        return String.format("%d - %s", this.getId(), this.getDescription());
    }

}
